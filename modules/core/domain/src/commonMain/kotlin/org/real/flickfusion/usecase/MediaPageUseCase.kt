package org.real.flickfusion.usecase

import org.real.flickfusion.model.Media
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 07/06/2024
 * Description: UseCase for fetching media data, page by page
 */
interface MediaPageUseCase<T : Media> {
    operator fun invoke(page: Int): Flow<Result<List<T>>>
}