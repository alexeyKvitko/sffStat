package ru.sff.statistic.activity;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.fragment.MenuFragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnMenuOptionSelectListener{

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initialize();
//        addReplaceFragment( MenuFragment.newInstance() );
    }


    private void initialize(){
        ( (TextView) findViewById( R.id.menuSelectTitleId )).setTypeface(AppConstants.ROTONDA_BOLD );
        ( (TextView) findViewById( R.id.queryAllDrawsId )).setTypeface(AppConstants.ROBOTO_CONDENCED );
        ( (TextView) findViewById( R.id.queryByDrawsId )).setTypeface(AppConstants.ROBOTO_CONDENCED );
        ( (TextView) findViewById( R.id.queryByMagnetId )).setTypeface(AppConstants.ROBOTO_CONDENCED );
        ( (TextView) findViewById( R.id.queryByDateId)).setTypeface(AppConstants.ROBOTO_CONDENCED );
        ( (TextView) findViewById( R.id.lotoTypeId )).setTypeface(AppConstants.ROBOTO_CONDENCED );
    }


    protected void addReplaceFragment( Fragment newFragment ) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations( R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out );
//        if ( getSupportFragmentManager().getFragments().size() == 0 ) {
//            fragmentTransaction.add( R.id.menuFragmentContainerId, newFragment );
//        } else {
//            fragmentTransaction.replace( R.id.menuFragmentContainerId, newFragment );
//            fragmentTransaction.addToBackStack( null );
//        }
//        fragmentTransaction.commit();
    }

    @Override
    public void onMainStatisticSelect() {

    }
}
