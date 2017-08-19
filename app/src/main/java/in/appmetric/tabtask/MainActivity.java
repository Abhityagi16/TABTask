package in.appmetric.tabtask;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.appmetric.tabtask.comicdetail.ComicDetailFragment;
import in.appmetric.tabtask.comicslist.ComicsListFragment;
import in.appmetric.tabtask.data.models.Comic;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    Fragment mComicListFragment;
    boolean isComicDetailOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mComicListFragment = new ComicsListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mComicListFragment)
                .commit();

    }

    @Override
    public void onComicClicked(Comic comic) {
        isComicDetailOpen = true;
        ComicDetailFragment comicDetail = new ComicDetailFragment();
        comicDetail.setComic(comic);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, comicDetail)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
