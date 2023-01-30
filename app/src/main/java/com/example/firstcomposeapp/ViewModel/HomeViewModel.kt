import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeapp.ApiService.ProductData
import com.example.firstcomposeapp.ApiService.ProductDataInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel: ViewModel() {
    val data = mutableStateOf<ProductData?>(null)
    val isLoading = mutableStateOf(false)
    val products = ProductDataInstance.getProduct.getProductInfo()

    fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true
            products.enqueue(object : Callback<ProductData> {
                override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                    val productData = response.body()
                    isLoading.value = false
                    if (productData != null) {
                        data.value = productData
                    }
                }
                override fun onFailure(call: Call<ProductData>, t: Throwable) {
                    if(data.value == null) {
                        isLoading.value = true
                    }
                    Log.d("Err", t.toString())
                    if(data.value == null) {
                        isLoading.value = false
                    }
                }
            })
        }
    }
}

