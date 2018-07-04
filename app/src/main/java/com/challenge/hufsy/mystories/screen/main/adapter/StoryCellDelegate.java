package com.challenge.hufsy.mystories.screen.main.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.challenge.hufsy.mystories.R;
import com.challenge.hufsy.mystories.app.base.BaseCellDelegate;
import com.challenge.hufsy.mystories.app.base.BaseViewHolder;
import com.challenge.hufsy.mystories.app.base.OnCellDelegateClickListener;
import com.challenge.hufsy.mystories.app.converter.EPOCHToStringConverter;
import com.challenge.hufsy.mystories.model.Story;

import butterknife.BindView;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class StoryCellDelegate extends BaseCellDelegate<Story> {

    private OnCellDelegateClickListener<Story> clickListener;

    public void setClickListener(OnCellDelegateClickListener<Story> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public boolean is(Story item) {
        return null != item;
    }

    @Override
    public BaseViewHolder<Story> holder(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.item_story, parent, false);

        return new StoryViewHolder(itemView);
    }

    class StoryViewHolder extends BaseViewHolder<Story> {

        @BindView(R.id.imgThumbNail)
        AppCompatImageView thumbNail;

        @BindView(R.id.tvFileName)
        TextView fileName;

        @BindView(R.id.tvFileDate)
        TextView fileDate;

        private View rootView;

        StoryViewHolder(View itemView) {
            super(itemView);

            this.rootView = itemView;
        }

        @Override
        public void bind(Story item) {
            Glide.with(rootView).load(item.getDownloadUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()).error(R.drawable.insert_photo))
                    .into(thumbNail);

            fileName.setText(item.getName());
            fileDate.setText(EPOCHToStringConverter.getStringFromEPOCH(item.getDate()));

            if (null != clickListener) {
                itemView.setOnClickListener((v) -> clickListener.onCellDelegateClick(itemView, getAdapterPosition(), item));
            }
        }
    }

}
