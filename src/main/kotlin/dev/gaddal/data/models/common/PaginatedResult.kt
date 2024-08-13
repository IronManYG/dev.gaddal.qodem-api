package dev.gaddal.data.models.common

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Generic data class for representing paginated results of any type.
 * This class is used to standardize the format of paginated responses throughout the application,
 * ensuring that pagination data is consistently handled and returned in API responses.
 *
 * @param T The type of the data items in the paginated result. This must extend [Any].
 * @property pageCount The total number of pages available based on the current query and page size.
 * @property nextPage The page number of the next page if available, or null if this is the last page.
 * @property data A list of data items of type [T], representing the contents of the current page.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude any null values from the JSON output to clean up the response.
data class PaginatedResult<T : Any>(
    val pageCount: Long,
    val nextPage: Long?,
    val data: List<T>
)
