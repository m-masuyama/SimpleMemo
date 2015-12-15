package jp.co.iseise.sample.simplememo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 * メモ変種
 */
public class EditActivity extends AppCompatActivity implements View.OnClickListener {
	public static final String MODE = "MODE";
	public static final String MEMO_ID = "MEMO_ID";
	public static final int MODE_NEW = 1;
	public static final int MODE_UPDATE = 2;
	private EditText editMemo;
	private Button buttonCancel;
	private Button buttonSave;
	private int mode;
	private long memoId;

	/**
	 * onCreate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(this);
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(this);
		editMemo = (EditText) findViewById(R.id.editMemo);
		mode = getIntent().getIntExtra(MODE, 0);
		if (mode == MODE_UPDATE) {
			memoId = getIntent().getLongExtra(MEMO_ID, 0);
			setMemoFofo();
		}
	}

	/**
	 * メモ情報を画面に設定
	 */
	private void setMemoFofo() {
		DbAdapter dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		Memo memo = dbAdapter.selectMemo(memoId);
		dbAdapter.close();
		if (memo == null) {
			return;
		}
		editMemo.setText(memo.getMemo());
	}

	/**
	 * ボタンクリック
	 */
	@Override
	public void onClick(View v) {
		if (v == buttonCancel) {
			finish();
		} else if (v == buttonSave) {
			if (mode == MODE_NEW) {
				insertMemo();
			} else if (mode == MODE_UPDATE) {
				updateMemo();
			}
			finish();
		}
	}

	/**
	 * メモのInsert
	 */
	private void insertMemo() {
		// 入力チェックは省略
		long now = Calendar.getInstance().getTimeInMillis();
		Memo memo = new Memo();
		memo.setMemo(editMemo.getText().toString());
		memo.setUpdated(now);
		DbAdapter dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		dbAdapter.insertMemo(memo);
		dbAdapter.close();
	}

	/**
	 * メモのUpdate
	 */
	private void updateMemo() {
		// 入力チェックは省略
		long now = Calendar.getInstance().getTimeInMillis();
		DbAdapter dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		Memo memo = dbAdapter.selectMemo(memoId);
		memo.setMemo(editMemo.getText().toString());
		memo.setUpdated(now);
		dbAdapter.updateMemo(memo);
		dbAdapter.close();
	}
}
