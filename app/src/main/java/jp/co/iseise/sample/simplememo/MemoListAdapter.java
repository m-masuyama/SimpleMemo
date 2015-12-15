package jp.co.iseise.sample.simplememo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * メモリストアダプター
 */
public class MemoListAdapter extends ArrayAdapter<Memo> {
	private List<Memo> items;
	private LayoutInflater inflater;
	private int viewResourceId;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

	/**
	 * コンストラクタ
	 * @param context
	 * @param viewResourceId ViewのリソースID
	 * @param items メモデータ
	 */
	public MemoListAdapter(Context context, int viewResourceId, List<Memo> items) {
		super(context, viewResourceId, items);
		this.items = items;
		this.viewResourceId = viewResourceId;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * View取得
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ビューを受け取る
		View view = convertView;
		if (view == null) {
			// 受け取ったビューがnullなら新しくビューを生成
			view = inflater.inflate(viewResourceId, null);
		}
		// 表示すべきデータの取得
		Memo memo = items.get(position);
		if (memo != null) {
			// メモを表示
			TextView textMemo = (TextView) view.findViewById(R.id.textMemo);
			if (textMemo != null) {
				textMemo.setText(memo.getMemo());
			}
			// 更新日時を表示
			TextView textDate = (TextView) view.findViewById(R.id.textDate);
			if (textDate != null) {
				textDate.setText(dateFormat.format(new Date(memo.getUpdated())));
			}
		}

		return view;
	}
}
