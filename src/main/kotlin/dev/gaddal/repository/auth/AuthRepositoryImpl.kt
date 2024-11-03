package dev.gaddal.repository.auth

import dev.gaddal.data.db.DatabaseFactory
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.BloodType
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import dev.gaddal.data.db.schemas.core_donation_management.enums.Gender
import dev.gaddal.data.models.entities.User
import dev.gaddal.data.models.entities.toUser
import dev.gaddal.data.models.params.LoginParams
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.utils.OperationError
import dev.gaddal.utils.PasswordHasher
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.datetime.toLocalDate
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll

/**
 * Implementation of [AuthRepository] that interacts with the database to perform user authentication operations.
 *
 * This class provides methods for registering, logging in, and finding users by email.
 */
class AuthRepositoryImpl : AuthRepository {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override suspend fun registerUser(userParams: UserParams): Result<User> {
        return try {
            DatabaseFactory.dbQuery {
                // Check if email or phone number already exists
                val existingUser = UserTable.selectAll().where {
                    (UserTable.email eq userParams.email) or (UserTable.phone_number eq userParams.phoneNumber)
                }.firstOrNull()

                if (existingUser != null) {
                    if (existingUser[UserTable.email] == userParams.email) {
                        throw IllegalStateException("Email already in use")
                    } else if (existingUser[UserTable.phone_number] == userParams.phoneNumber) {
                        throw IllegalStateException("Phone number already in use")
                    }
                }

                // If we reach here, no existing user was found, so proceed with insertion
                // Hash the password using Argon2 before storing it
                val hashedPassword = PasswordHasher.hashPassword(userParams.password)

                val userId = UserTable.insert {
                    it[birth_date] = userParams.birthDate.toLocalDate()
                    it[gender] = Gender.valueOf(userParams.gender)
                    it[postal_code] = userParams.postalCode
                    it[street] = userParams.street
                    it[city] = userParams.city
                    it[state] = userParams.state
                    it[country] = userParams.country
                    it[phone_number] = userParams.phoneNumber
                    it[email] = userParams.email
                    it[password] = hashedPassword  // Store the hashed password
                    it[emergency_contact] = userParams.emergencyContact
                    it[image_url] = userParams.imageUrl
                    it[blood_type] = BloodType.valueOf(userParams.bloodType)
                    it[weight] = userParams.weight
                    it[height] = userParams.height
                } get UserTable.id

                UserNameTable.insert {
                    it[user_id] = userId.value
                    it[first_name] = userParams.firstName
                    it[middle_name] = userParams.middleName
                    it[last_name] = userParams.lastName
                }

                (UserTable innerJoin UserNameTable)
                    .selectAll().where { UserTable.id eq userId }
                    .first()
                    .toUser()
            }
        } catch (e: IllegalStateException) {
            logger.error(e) { "Conflict while registering user: ${e.message}" }
            Result.Error(OperationError.Conflict(e.message ?: "Conflict occurred during registration"))
        } catch (e: Exception) {
            logger.error(e) { "Error registering user" }
            Result.Error(OperationError.Database("Error registering user: ${e.message}"))
        }.let { result ->
            when (result) {
                is User -> Result.Success(result)
                is Result.Error -> result
                else -> Result.Error(OperationError.Database("Unexpected result when registering user"))
            }
        }
    }

    override suspend fun loginUser(loginParams: LoginParams): Result<User> {
        return try {
            val userResultRow = DatabaseFactory.dbQuery {
                (UserTable innerJoin UserNameTable)
                    .selectAll().where { UserTable.email eq loginParams.email }
                    .firstOrNull()
            }
            val user = userResultRow?.toUser()

            if (user != null && PasswordHasher.verifyPassword(loginParams.password, userResultRow[UserTable.password])) {
                logger.info { "User logged in successfully: ${user.id}" }
                Result.Success(user)
            } else {
                Result.Error(OperationError.NotFound("Invalid email or password"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error logging in user" }
            Result.Error(OperationError.Database("Error logging in user"))
        }
    }

    override suspend fun findUserByEmail(email: String): Result<User> {
        return try {
            val user = DatabaseFactory.dbQuery {
                (UserTable innerJoin UserNameTable)
                    .selectAll().where { UserTable.email eq email }
                    .firstOrNull()
                    ?.toUser()
            }
            if (user != null) {
                logger.info { "User found by email: ${user.id}" }
                Result.Success(user)
            } else {
                Result.Error(OperationError.NotFound("User with email $email not found"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error finding user by email" }
            Result.Error(OperationError.Database("Error finding user by email"))
        }
    }
}
