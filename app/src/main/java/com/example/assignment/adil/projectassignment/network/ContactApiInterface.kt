import com.example.assignment.adil.projectassignment.network.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/")
    suspend fun getRandomUsers(@Query("results") results: Int = 50): RandomUserResponse
}
