package learning.nitish.redmart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import learning.nitish.redmart.R;
import learning.nitish.redmart.adapter.ImageSliderAdapter;
import learning.nitish.redmart.model.Product;
import learning.nitish.redmart.model.SingleProduct;
import learning.nitish.redmart.network.Api;
import learning.nitish.redmart.network.RestClientAdapter;
import learning.nitish.redmart.util.Constants;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.indicator)
    CircleIndicator circleIndicator;
    @BindView(R.id.txtProdName)
    TextView txtProdName;
    @BindView(R.id.txtProdQty)
    TextView txtProdQty;
    @BindView(R.id.txtPrice)
    TextView txtActualPrice;
    @BindView(R.id.txtSavePrice)
    TextView txtSavePrice;
    @BindView(R.id.txtDis)
    TextView txtDiscount;
    @BindView(R.id.txtDesc)
    TextView txtProdDesc;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    HiveProgressView progressView;

    int productID = 0;

    ImageSliderAdapter sliderAdapter;
    Api apiAdapter;
    


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setUpWindowsAnimation();
        Intent intent = getIntent();
        if (intent != null) {
            productID = intent.getIntExtra(Constants.PRODUCT_ID, 0);
        }
        intiViews();
        setUpRestAdapter();


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpWindowsAnimation() {

        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.transition_fade);
        getWindow().setEnterTransition(fade);
    }

    private void intiViews() {


        toolbar.setTitle(getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressView.setRainbow(false);
        progressView.setColor(R.color.blue_strip_color);
        progressView.setBackgroundColor(Color.TRANSPARENT);
        progressView.setVisibility(View.VISIBLE);

    }

    private void setUpRestAdapter() {

        apiAdapter = RestClientAdapter.createRestAdapter(Api.class, Constants.BASE_URL, this);
        getProductData();
    }

    private void getProductData() {


        Call<SingleProduct> singleProductCall = apiAdapter.getProductDetail(productID);
        singleProductCall.enqueue(new Callback<SingleProduct>() {
            @Override
            public void onResponse(Call<SingleProduct> call, Response<SingleProduct> response) {
                if (response.isSuccessful()) {
                    setData(response.body().getProduct());
                    progressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SingleProduct> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressView.setVisibility(View.GONE);
            }
        });

    }

    private void setData(Product product) {

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        ArrayList<String> images = new ArrayList<>();
        for (int i = 0; i < product.getImages().size(); i++) {
            images.add(Constants.IMAGE_BASE_URL.concat(product.getImages().get(i).getName()));
        }

        sliderAdapter = new ImageSliderAdapter(images, DetailActivity.this);
        mPager.setAdapter(sliderAdapter);
        circleIndicator.setViewPager(mPager);

        txtProdName.setText(product.getTitle());
        toolbar.setTitle(product.getTitle());
        txtProdQty.setText(product.getMeasure().getWtOrVol());
        txtProdDesc.setText(product.getDesc());


        if (product.getPricing().getPromoPrice() != null && product.getPricing().getPromoPrice() != 0) {

            txtSavePrice.setVisibility(View.VISIBLE);
            txtActualPrice.setText(numberFormat.format(product.getPricing().getPromoPrice()));
            txtActualPrice.setTextColor(getResources().getColor(R.color.redmart_red));
            txtSavePrice.setText(numberFormat.format(product.getPricing().getPrice()));
            txtSavePrice.setPaintFlags(txtSavePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            txtSavePrice.setVisibility(View.GONE);
            txtActualPrice.setText(numberFormat.format(product.getPricing().getPrice()));
        }


        if (product.getPricing().getSavingsText() != null) {
            if (product.getPricing().getSavingsText().length() > 0) {
                txtDiscount.setVisibility(View.VISIBLE);
                txtDiscount.setText(product.getPricing().getSavingsText());
            } else {
                txtDiscount.setVisibility(View.INVISIBLE);
            }
        } else {

            txtDiscount.setVisibility(View.INVISIBLE);
        }

    }
}
