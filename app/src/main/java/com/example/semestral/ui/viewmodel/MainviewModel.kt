import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.semestral.models.Comida
import com.example.semestral.vo.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {

    private val _selectedMeal = MutableLiveData<Resource<Comida>>()
    val selectedMeal: LiveData<Resource<Comida>>
        get() = _selectedMeal

    private val _favoriteRecipes = MutableLiveData<Resource<List<Comida>>>()
    val favoriteRecipes: LiveData<Resource<List<Comida>>>
        get() = _favoriteRecipes

    fun fetchMealDetails(idMeal: String) {
        viewModelScope.launch {
            _selectedMeal.value = Resource.Loading()
            _selectedMeal.value = repo.fetchMealDetails(idMeal)
        }
    }

    fun toggleFavoriteRecipe(comida: Comida) {
        viewModelScope.launch {
            repo.toggleFavoriteRecipe(comida)
        }
    }

    fun fetchFavoriteRecipes() {
        viewModelScope.launch {
            _favoriteRecipes.value = Resource.Loading()
            _favoriteRecipes.value = repo.fetchFavoriteRecipes()
        }
    }
}
