package cn.junecai.voa.activity;

import android.app.Fragment;

/**
 * Created by admin on 13-11-23.
 */
public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = new AttentionFragment();;
        switch (index) {
            case 1:
                fragment = new cn.junecai.voa.activity.MyListFragment();
                break;
            case 2:
                fragment = new AttentionFragment();
                break;
            case 3:
                fragment = new CommentFragment();
                break;
            case 4:
                fragment = new cn.junecai.voa.activity.GlobalFragment();
                break;
            case 5:
                fragment = new AtmeFragment();
                break;
        }
        return fragment;
    }
}
