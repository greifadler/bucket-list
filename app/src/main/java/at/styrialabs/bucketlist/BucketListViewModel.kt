package at.styrialabs.bucketlist

import androidx.lifecycle.*
import at.styrialabs.bucketlist.domain.BucketListElement
import at.styrialabs.bucketlist.repository.BucketListElementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class BucketListViewModel @Inject constructor(
    private val bucketListElementRepository: BucketListElementRepository,
) : ViewModel() {
    fun addBucketList(textState: String) {
        viewModelScope.launch (Dispatchers.IO) {
            bucketListElementRepository.insertAll(BucketListElement(text = textState, createdAt = LocalDateTime.now()))
        }
    }

    fun setToDone(element: BucketListElement) {
        viewModelScope.launch (Dispatchers.IO) {
            bucketListElementRepository.updateAll(element.copy(doneAt = LocalDateTime.now()))
        }
    }

    fun delete(element: BucketListElement) {
        viewModelScope.launch (Dispatchers.IO) {
            bucketListElementRepository.deleteAll(element)
        }
    }

    val bucketLists: LiveData<List<BucketListElement>> by lazy {
        bucketListElementRepository.getAll()
    }}