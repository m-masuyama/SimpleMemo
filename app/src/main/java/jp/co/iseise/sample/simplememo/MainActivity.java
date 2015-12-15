package jp.co.iseise.sample.simplememo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
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
		DbAdapter dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		List<Memo> list = dbAdapter.selectAllMemos();
		listMemo.clear();
		listMemo.addAll(list);
		dbAdapter.close();
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
			Intent intent = new Intent(this, EditActivity.class);
			intent.putExtra(EditActivity.MODE, EditActivity.MODE_NEW);
			startActivity(intent);
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
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra(EditActivity.MODE, EditActivity.MODE_UPDATE);
		intent.putExtra(EditActivity.MEMO_ID, memo.getId());
		startActivity(intent);
	}
}
