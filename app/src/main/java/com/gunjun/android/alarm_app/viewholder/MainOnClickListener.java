package com.gunjun.android.alarm_app.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gunjun.android.alarm_app.AlarmDetailActivity;
import com.gunjun.android.alarm_app.R;

/**
 * Created by gunjunLee on 2017-01-28.
 */

public class MainOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        int id = Integer.parseInt(((TextView) v.findViewById(R.id.item_id)).getText().toString());
        Intent intent = new Intent(v.getContext(), AlarmDetailActivity.class);
        intent.putExtra("alarm_id", id);
        v.getContext().startActivity(intent);
    }
}
