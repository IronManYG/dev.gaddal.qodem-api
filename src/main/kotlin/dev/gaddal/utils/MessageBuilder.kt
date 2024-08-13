package dev.gaddal.utils

/**
 * Utility class for building standardized messages related to entity operations.
 *
 * This class generates consistent success and error messages for CRUD operations
 * and other common scenarios, parameterized by the entity name.
 *
 * @property entity The name of the entity for which messages are being generated.
 */
class MessageBuilder(private val entity: String) {

    /**
     * Generates a success message for entity creation.
     *
     * @return A string indicating successful creation of the entity.
     */
    fun createdSuccess() = "$entity successfully created"

    /**
     * Generates a success message for entity update.
     *
     * @return A string indicating successful update of the entity.
     */
    fun updatedSuccess() = "$entity successfully updated"

    /**
     * Generates a success message for entity deletion.
     *
     * @return A string indicating successful deletion of the entity.
     */
    fun deletedSuccess() = "$entity successfully deleted"

    /**
     * Generates a message for when an entity is not found.
     *
     * @return A string indicating that the entity was not found.
     */
    fun notFound() = "$entity not found"

    /**
     * Generates a message for general processing errors related to the entity.
     *
     * @return A string indicating that an error occurred while processing the entity.
     */
    fun processingError() = "An error occurred while processing the $entity"

    /**
     * Generates a generic success message.
     *
     * @return A string indicating a successful operation.
     */
    fun success() = "Success"
}