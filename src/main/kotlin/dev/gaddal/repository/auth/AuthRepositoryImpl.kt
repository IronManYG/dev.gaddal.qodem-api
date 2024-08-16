package dev.gaddal.repository.auth

import dev.gaddal.data.db.DatabaseFactory
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.BloodType
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import dev.gaddal.data.db.schemas.core_donation_management.enums.Gender
import dev.gaddal.data.models.entities.User
import dev.gaddal.data.models.entities.toUser
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.utils.OperationError
import dev.gaddal.utils.PasswordHasher
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.datetime.toLocalDate
import org.jetbrains.exposed.sql.insert
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
            val user = DatabaseFactory.dbQuery {
                // Hash the password using Argon2 before storing it
                val hashedPassword = PasswordHasher.hashPassword(userParams.password)

                // Insert user details first
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

                // Insert username with reference to user
                UserNameTable.insert {
                    it[user_id] = userId.value
                    it[first_name] = userParams.firstName
                    it[middle_name] = userParams.middleName
                    it[last_name] = userParams.lastName
                }

                // Retrieve the newly created user
                (UserTable innerJoin UserNameTable)
                    .selectAll().where { UserTable.id eq userId }
                    .first()
                    .toUser()
            }
            if (user != null) {
                Result.Success(user)
            } else {
                Result.Error(OperationError.Database("Failed to insert user"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error adding user" }
            Result.Error(OperationError.Database("Error adding user"))
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val userResultRow = DatabaseFactory.dbQuery {
                (UserTable innerJoin UserNameTable)
                    .selectAll().where { UserTable.email eq email }
                    .firstOrNull()
            }
            val user = userResultRow?.toUser()

            if (user != null && PasswordHasher.verifyPassword(password, userResultRow[UserTable.password])) {
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
