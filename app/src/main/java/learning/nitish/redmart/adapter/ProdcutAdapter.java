package learning.nitish.redmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import learning.nitish.redmart.R;
import learning.nitish.redmart.interfaces.ItemClickListner;
import learning.nitish.redmart.model.Product;
import learning.nitish.redmart.util.Constants;

/**
 * Created by Nitish Singh Rathore on 21/8/17.
 */

public class ProdcutAdapter extends RecyclerView.Adapter<ProdcutAdapter.ProductViewHolder> {


    Context mContext;
    List<Product> mProductsList = new ArrayList<>();
    ItemClickListner mItemClickListner;

    public ProdcutAdapter(Context mContext, ItemClickListner itemClickListner) {
        this.mContext = mContext;
        this.mItemClickListner = itemClickListner;


    }

    @Override
    public ProdcutAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product_layout, parent, false);
        return new ProdcutAdapter.ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {

        final Product product = mProductsList.get(position);

        final ProductViewHolder productViewHolder = (ProductViewHolder) holder;

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        productViewHolder.txtProductName.setText(mProductsList.get(position).getTitle());
        if (product.getPricing().getPromoPrice() != null && product.getPricing().getPromoPrice() != 0) {

            productViewHolder.txtProdDis.setVisibility(View.VISIBLE);
            productViewHolder.txtProductPrice.setText(numberFormat.format(product.getPricing().getPromoPrice()));
            productViewHolder.txtProductPrice.setTextColor(mContext.getResources().getColor(R.color.redmart_red));
            productViewHolder.txtProdDis.setText(numberFormat.format(product.getPricing().getPrice()));
            productViewHolder.txtProdDis.setPaintFlags(productViewHolder.txtProdDis.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            productViewHolder.txtProdDis.setVisibility(View.GONE);
            productViewHolder.txtProductPrice.setTextColor(Color.BLACK);
            productViewHolder.txtProductPrice.setText(numberFormat.format(product.getPricing().getPrice()));
        }

        Glide.with(mContext).load(Constants.IMAGE_BASE_URL.concat(product.getImages().get(0).getName())).into(productViewHolder.imgProductPic);


        if (product.getMeasure().getWtOrVol() != null) {
            productViewHolder.txtProdQty.setText(product.getMeasure().getWtOrVol());
        }

        if (product.getPricing().getSavingsText() != null) {
            if (product.getPricing().getSavingsText().length() > 0) {

                productViewHolder.txtProdSaveText.setVisibility(View.VISIBLE);
                productViewHolder.txtProdSaveText.setText(product.getPricing().getSavingsText());
            } else {
                productViewHolder.txtProdSaveText.setVisibility(View.INVISIBLE);
            }
        } else {
            productViewHolder.txtProdSaveText.setVisibility(View.INVISIBLE);
        }

        productViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListner.onItemClick(position, product.getId(), view);
            }
        });


    }


    @Override
    public int getItemCount() {


        return mProductsList == null ? 0 : mProductsList.size();
    }


    public void addAll(List<Product> mProducts) {
        mProductsList.addAll(mProducts);
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvMainLayout)
        CardView cardView;
        @BindView(R.id.productName)
        TextView txtProductName;
        @BindView(R.id.productPrice)
        TextView txtProductPrice;
        @BindView(R.id.productImage)
        ImageView imgProductPic;
        @BindView(R.id.txtProductSaving)
        TextView txtProdSaveText;
        @BindView(R.id.productQty)
        TextView txtProdQty;
        @BindView(R.id.productDiscountedPrice)
        TextView txtProdDis;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


    class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
