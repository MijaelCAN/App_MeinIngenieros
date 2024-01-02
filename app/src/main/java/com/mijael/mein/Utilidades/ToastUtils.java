package com.mijael.mein.Utilidades;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mijael.mein.R;

public class ToastUtils {
    public static void showCustomToast(Context context, String message, int tiempo) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.mensaje_exitoso, null);

        TextView text = layout.findViewById(R.id.textViewToast);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP, 0, 0);
        if (tiempo > 0) {
            toast.setDuration(tiempo);
        }
        toast.show();
    }
}
