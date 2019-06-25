package ru.sff.statistic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageView;

import ru.sff.statistic.R;
import ru.sff.statistic.component.AppHeader;
import ru.sff.statistic.fragment.AllResultsFragment;

public class RouteActivity extends BaseActivity implements AllResultsFragment.OnMenuOptionSelectListener{

    private AppHeader mHeader;
    private ImageView mBackBtn;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_route );
        initialize();
    }

    private void initialize(){
        mActivityContainer = findViewById( R.id.routeActivityLayoutId );
        mHeader = findViewById( R.id.routeHeaderId );
        mBackBtn = findViewById( R.id.routeBackBtnId );
        addReplaceFragment( AllResultsFragment.newInstance(), R.drawable.emoji_look, R.string.all_draws_label );
    }

    protected void addReplaceFragment( Fragment newFragment, int emojiId, int titleId ) {
        mHeader.setHeader( emojiId, titleId );
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out );
        if ( getSupportFragmentManager().getFragments().size() == 0 ) {
            fragmentTransaction.add( R.id.routeFragmentContainerId, newFragment );
        } else {
            fragmentTransaction.replace( R.id.routeFragmentContainerId, newFragment );
            fragmentTransaction.addToBackStack( null );
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onMainStatisticSelect() {

    }

    public ImageView getBackBtn() {
        return mBackBtn;
    }
}