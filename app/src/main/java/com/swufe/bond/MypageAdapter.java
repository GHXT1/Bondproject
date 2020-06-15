package com.swufe.bond;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class MypageAdapter extends FragmentPagerAdapter {
    private  String[] title=new String[]{"债务信息","用户","。。。。。"};

    public MypageAdapter(FragmentManager fm){
        super(fm);
    }


    public Fragment getItem(int position) {
        if(position==0){
            return new FirstFragment1();
        }else if(position==1){
            return new SecondFragment2();
        }else{
            return new ThirdFragment3();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
