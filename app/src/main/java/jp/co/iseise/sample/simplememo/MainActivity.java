package jp.co.iseise.sample.simplememo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
	private ListView listView;
	private List<Memo> listMemo = new ArrayList<Memo>();
	private MemoListAdapter memoListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// レイアウトからListViewを取得
		listView = (ListView) findViewById(R.id.listMemo);
		// 行クリックのイベントリスナー登録
		listView.setOnItemClickListener(this);
		// メモリストアダプター作成
		memoListAdapter = new MemoListAdapter(this, R.layout.row_memo, listMemo);
		listView.setAdapter(memoListAdapter);
		// メモデータ読み込み
		readMemos();
	}

	/**
	 * メモデータ読み込み
	 */
	private void readMemos() {
		for (int i = 0; i < 20; i++) {
			Memo memo = new Memo();
			memo.setMemo("めも" + (i + 1));
			memo.setUpdated(new Date().getTime());
			listMemo.add(memo);
		}
		memoListAdapter.notifyDataSetChanged();
	}

	/**
	 * メニュー作成
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	/**
	 * メニュー選択
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.action_new_memo) {
			Toast.makeText(this, "新規メモ", Toast.LENGTH_LONG).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * リストビューの行クリック
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Memo memo = listMemo.get(position);
		Toast.makeText(this, "行クリック:" + memo.getMemo(), Toast.LENGTH_LONG).show();
	}
}
