package com.dat.testviews;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by DAT on 23-Apr-16.
 */
public class AnimationChangeTestFragment extends Fragment {

    @Bind(R.id.search)
    protected EditText search;
    @Bind(R.id.results)
    protected RecyclerView results;
    private SimpleStringRecyclerViewAdapter adapter;
    @Bind(R.id.container)
    protected CardView container;
    private View view;

    List<String> randomList;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        LayoutTransition layoutTransition = container.getLayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_animation_change_test, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void setupRecyclerView() {
        randomList = getRandomSublist(Cheeses.sCheeseStrings, 30);
        results.setLayoutManager(new LinearLayoutManager(results.getContext()));
        adapter = new SimpleStringRecyclerViewAdapter(getActivity(),
                getRandomSublist(Cheeses.sCheeseStrings, 0));
        results.setAdapter(adapter);

    }

    private List<String> getRandomSublist(String[] array, int amount) {
        List<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    @OnTextChanged(R.id.search)
    protected void search() {
        if (search.getText().toString().isEmpty()) {
            adapter.setData(getRandomSublist(Cheeses.sCheeseStrings, 0));
        } else {
            List<String> foundItems = new ArrayList<>();
            for (String item : randomList) {
                if (item.contains(search.getText().toString())) {
                    foundItems.add(item);
                }
            }
            adapter.setData(foundItems);
        }
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<String> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(Cheeses.getRandomCheeseDrawable())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        public void setData(List<String> data) {
            mValues.clear();
            mValues.addAll(data);
            notifyDataSetChanged();
        }

        public List<String> getmValues() {
            return mValues;
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
