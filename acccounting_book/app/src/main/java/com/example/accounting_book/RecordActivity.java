package com.example.accounting_book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.accounting_book.adapter.RecordPagerAdapter;
import com.example.accounting_book.frag_record.IncomeFragment;
import com.example.accounting_book.frag_record.OutcomeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;  // 改用ViewPager2
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // 查找控件（注意ID改为record_vp2）
        tabLayout = findViewById(R.id.record_tabs);
        viewPager2 = findViewById(R.id.record_vp2);

        initPager();
    }

    private void initPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OutcomeFragment());
        fragments.add(new IncomeFragment());

        // 使用FragmentStateAdapter替代旧版PagerAdapter
        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return fragments.size();
            }

            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }
        };

        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(position == 0 ? "支出" : "收入");
        }).attach();
    }

    /* 点击事件*/
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.record_iv_back) {
            finish();
        }
    }

}
