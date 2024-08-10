package dev.gaddal.repository.core_donation_management

import dev.gaddal.data.db.DatabaseFactory
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.BloodType
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import dev.gaddal.data.db.schemas.core_donation_management.enums.Gender
import dev.gaddal.data.models.common.PaginatedResult
import dev.gaddal.data.models.entities.User
import dev.gaddal.data.models.entities.toUser
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.utils.OperationError
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.datetime.toLocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

/**
 * Implementation of [UserRepository] that interacts with the database to perform user-related operations.
 *
 * This class provides methods for CRUD operations on users, as well as pagination and error handling.
 */
class UserRepositoryImpl : UserRepository {
    private val logger = KotlinLogging.logger {}

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A [Result] containing the [User] if found, or an error if not found or if a database error occurs.
     */
    override suspend fun getUserById(id: Int): Result<User> {
        return try {
            val user = DatabaseFactory.dbQuery {
                (UserTable innerJoin UserNameTable)
                    .selectAll().where { UserTable.id eq id }
                    .firstOrNull()
                    ?.toUser()
            }
            if (user != null) {
                Result.Success(user)
            } else {
                Result.Error(OperationError.NotFound("User with id $id not found"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving user with id $id" }
            Result.Error(OperationError.Database("Error retrieving user"))
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A [Result] containing a list of all [User]s, or an error if a database error occurs.
     */
    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val users = DatabaseFactory.dbQuery {
                (UserTable innerJoin UserNameTable).selectAll().mapNotNull { it.toUser() }
            }
            Result.Success(users)
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving users" }
            Result.Error(OperationError.Database("Error retrieving users"))
        }
    }

    /**
     * Retrieves a paginated list of users.
     *
     * @param page The page number to retrieve (1-indexed).
     * @param limit The maximum number of users per page.
     * @return A [Result] containing a [PaginatedResult] of [User]s, or an error if a database error occurs.
     */
    override suspend fun getUsers(page: Int, limit: Int): Result<PaginatedResult<User>> {
        return try {
            DatabaseFactory.dbQuery {
                val totalCount = UserTable.selectAll().count()
                val totalPages = (totalCount / limit) + if (totalCount % limit > 0) 1 else 0
                val offset = (page - 1) * limit

                val users = (UserTable innerJoin UserNameTable)
                    .selectAll()
                    .orderBy(UserTable.id, SortOrder.ASC)
                    .limit(limit, offset.toLong())
                    .mapNotNull { it.toUser() }

                val nextPage = if (page < totalPages) page + 1 else null

                Result.Success(PaginatedResult(totalPages, nextPage?.toLong(), users))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving paginated users" }
            Result.Error(OperationError.Database("Error retrieving paginated users"))
        }
    }

    /**
     * Adds a new user to the database.
     *
     * @param userParams The parameters for creating the new user.
     * @return A [Result] containing the newly created [User], or an error if creation fails.
     */
    override suspend fun addUser(userParams: UserParams): Result<User> {
        return try {
            val user = DatabaseFactory.dbQuery {
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

    /**
     * Updates an existing user in the database.
     *
     * @param id The unique identifier of the user to update.
     * @param userParams The new parameters for the user.
     * @return A [Result] containing the updated [User], or an error if update fails or user is not found.
     */
    override suspend fun updateUser(id: Int, userParams: UserParams): Result<User> {
        return try {
            val updated = DatabaseFactory.dbQuery {
                // Update user details
                val updateCount = UserTable.update({ UserTable.id eq id }) {
                    it[birth_date] = userParams.birthDate.toLocalDate()
                    it[gender] = Gender.valueOf(userParams.gender)
                    it[postal_code] = userParams.postalCode
                    it[street] = userParams.street
                    it[city] = userParams.city
                    it[state] = userParams.state
                    it[country] = userParams.country
                    it[phone_number] = userParams.phoneNumber
                    it[email] = userParams.email
                    it[emergency_contact] = userParams.emergencyContact
                    it[image_url] = userParams.imageUrl
                    it[blood_type] = BloodType.valueOf(userParams.bloodType)
                    it[weight] = userParams.weight
                    it[height] = userParams.height
                }

                if (updateCount > 0) {
                    // Update user name
                    UserNameTable.update({ UserNameTable.user_id eq id }) {
                        it[first_name] = userParams.firstName
                        it[middle_name] = userParams.middleName
                        it[last_name] = userParams.lastName
                    }

                    // Retrieve the updated user
                    (UserTable innerJoin UserNameTable)
                        .selectAll().where { UserTable.id eq id }
                        .first()
                        .toUser()
                } else {
                    null
                }
            }

            if (updated != null) {
                Result.Success(updated)
            } else {
                Result.Error(OperationError.NotFound("User with id $id not found"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error updating user" }
            Result.Error(OperationError.Database("Error updating user"))
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The unique identifier of the user to delete.
     * @return A [Result] indicating success (true) or failure (error) of the deletion operation.
     */
    override suspend fun deleteUser(userId: Int): Result<Boolean> {
        return try {
            val deleted = DatabaseFactory.dbQuery {
                // Check if the user exists
                val userExists = UserTable.selectAll().where { UserTable.id eq userId }.firstOrNull() != null

                if (userExists) {
                    // Delete the user's name entry first
                    UserNameTable.deleteWhere { user_id eq userId }

                    // Then delete the user
                    UserTable.deleteWhere { UserTable.id eq userId } > 0
                } else {
                    false
                }
            }

            if (deleted) {
                Result.Success(true)
            } else {
                Result.Error(OperationError.NotFound("User with id $userId not found"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error deleting user" }
            Result.Error(OperationError.Database("Error deleting user"))
        }
    }
}