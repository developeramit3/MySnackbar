package com.developeramit.customsnackbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;


public class MySnackbar {
    // main context items
    private static Context snackContext;
    private static Snackbar snackbar;
    private static MySnackbar singleton;

    // variables
    private static int colorCode = Type.getColorCode(Type.SUCCESS);
    private static String snackMessage = "Hi there !";
    private static int snackDuration = Duration.getDuration(Duration.SHORT);
    private static View view;

    private static boolean isCustomView;
    private static boolean isFillParent;
    private static Align textAlign;

    public static MySnackbar with(Context context, View fab) {
        try {
            snackContext = context.getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (singleton == null)
            singleton = new MySnackbar();

        if (fab == null) {
            View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
            view = rootView;
            snackbar = Snackbar
                    .make(view, "", snackDuration);
        } else {
            view = fab;
            snackbar = Snackbar
                    .make(view, "", snackDuration);
        }

        isCustomView = false;
        isFillParent = false;
        textAlign = Align.LEFT;

        return singleton;
    }

    public MySnackbar type(Type type) {
        colorCode = Type.getColorCode(type);
        return singleton;
    }

    public MySnackbar type(Type type, int color) {
        if (type == Type.CUSTOM)
            colorCode = color;
        else
            colorCode = Type.getColorCode(type);
        return singleton;
    }

    public MySnackbar message(CharSequence displayingMessage) {
        snackMessage = displayingMessage.toString();
        return singleton;
    }

    public MySnackbar duration(Duration duration) {
        if (duration != Duration.CUSTOM) {
            snackDuration = Duration.getDuration(duration);
        }
        return singleton;
    }

    public MySnackbar duration(Duration durationType, int duration) {
        if (durationType == Duration.CUSTOM) {
            snackDuration = duration;
        }
        return singleton;
    }

    public MySnackbar contentView(final View view, int heightInDp) {
        isCustomView = true;

        final Snackbar.SnackbarLayout snackLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        Snackbar.SnackbarLayout.LayoutParams params =
                (Snackbar.SnackbarLayout.LayoutParams) snackLayout.getLayoutParams();

        params.height = (int) pxFromDp(heightInDp);

        TextView textView = (TextView) snackLayout.findViewById(R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        snackLayout.addView(view, 0, params);
        return singleton;
    }

    public static MySnackbar fillParent(boolean fillParent) {
        isFillParent = fillParent;
        return singleton;
    }

    public static MySnackbar textAlign(Align align) {
        textAlign = align;
        return singleton;
    }

    private static View getSnackBarLayout() {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private static MySnackbar setColor(int colorId) {
        View snackBarView = getSnackBarLayout();
        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorId);
        }

        return singleton;
    }

    private static void setTextAlignment(Snackbar snackbar) {
        TextView textView = (TextView) snackbar.getView().findViewById(R.id.snackbar_text);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        textView.setLayoutParams(params);

        switch (textAlign) {
            case CENTER:
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case RIGHT:
                    textView.setGravity(Gravity.RIGHT);
                break;
            case LEFT:
                    textView.setGravity(Gravity.LEFT);
                break;
            default:
                    textView.setGravity(Gravity.LEFT);
                break;
        }
    }

    public  void show() {
        if (isCustomView) {
            snackbar.setDuration(snackDuration);
            snackbar.show();
        } else {
            snackbar = Snackbar
                    .make(view, snackMessage, snackDuration)
                    .setDuration(snackDuration);

            if (isFillParent)
                snackbar.getView().getLayoutParams().width = AppBarLayout.LayoutParams.MATCH_PARENT;

            setTextAlignment(snackbar);

            setColor(colorCode);

            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            textView.setMaxLines(10);
        }
        snackbar.show();
    }

    private  float pxFromDp(int dp) {
        return dp * snackContext.getResources().getDisplayMetrics().density;
    }

    public  void dismiss() {
        if (snackbar != null) {
            if (snackbar.isShown()) {
                snackbar.dismiss();
            }
        }
    }
}

