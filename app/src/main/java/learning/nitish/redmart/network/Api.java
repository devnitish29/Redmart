package learning.nitish.redmart.network;

import learning.nitish.redmart.model.ProductResponse;
import learning.nitish.redmart.model.SingleProduct;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nitish Singh Rathore on 21/8/17.
 */

public interface Api {


    @GET("search")
    Call<ProductResponse> getProducts(@Query(value = "page") int pageNo, @Query(value = "pageSize") int pageSize);


    @GET("products/{product_id}")
    Call<SingleProduct> getProductDetail(@Path("product_id") int product_id);
}
