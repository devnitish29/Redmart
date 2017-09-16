package learning.nitish.redmart.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import learning.nitish.redmart.R;
import learning.nitish.redmart.adapter.ProdcutAdapter;
import learning.nitish.redmart.interfaces.ItemClickListner;
import learning.nitish.redmart.model.Product;
import learning.nitish.redmart.model.ProductResponse;
import learning.nitish.redmart.network.Api;
import learning.nitish.redmart.network.RestClientAdapter;
import learning.nitish.redmart.util.Constants;
import learning.nitish.redmart.util.PaginationScrollListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenActivity extends AppCompatActivity implements ItemClickListner {

    //views
    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    HiveProgressView hiveProgressView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    //int variables
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    int TOTAL_PAGES = 20;
    int PAGE_SIZE = 20;
    private int currentPage = PAGE_START;


    //adapters
    Api clientAdapter;
    ProdcutAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpClientAdapter();
        initViews();


    }


    // method to get all the products using network calls
    private void getProducts(int page_no) {
        hiveProgressView.setVisibility(View.VISIBLE);
        Call<ProductResponse> fetchIntialProducts = clientAdapter.getProducts(page_no, PAGE_SIZE);

        fetchIntialProducts.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {

                    isLoading = false;
                    hiveProgressView.setVisibility(View.GONE);

                    List<Product> products = response.body().getProducts();
                    adapter.addAll(products);


                    if (currentPage > TOTAL_PAGES) {
                        isLastPage = true;
                    }


                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(HomeScreenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                hiveProgressView.setVisibility(View.GONE);
            }
        });
    }


    //method to initate view and click listeners
    private void initViews() {

        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        hiveProgressView.setRainbow(false);
        hiveProgressView.setColor(R.color.blue_strip_color);
        hiveProgressView.setBackgroundColor(Color.TRANSPARENT);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ProdcutAdapter(HomeScreenActivity.this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                getProducts(currentPage);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        getProducts(currentPage);

    }


    //method to setup retrofit adapter for network call
    private void setUpClientAdapter() {

        clientAdapter = RestClientAdapter.createRestAdapter(Api.class, Constants.BASE_URL, this);


    }


    //method to transit to new screen with animation
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position, int productId, View view) {

        Intent intent = new Intent(HomeScreenActivity.this, DetailActivity.class);

        String transitionName = getString(R.string.trans_name);

        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, view, transitionName);

        intent.putExtra(Constants.PRODUCT_ID, productId);
        startActivity(intent, transitionActivityOptions.toBundle());


    }
}
