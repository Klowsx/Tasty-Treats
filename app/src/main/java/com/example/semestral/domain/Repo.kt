import com.example.semestral.models.Comida
import com.example.semestral.vo.Resource

interface Repo {
    suspend fun fetchMealDetails(idMeal: String): Resource<Comida>
    suspend fun toggleFavoriteRecipe(comida: Comida)
    suspend fun fetchFavoriteRecipes(): Resource<List<Comida>>
}
