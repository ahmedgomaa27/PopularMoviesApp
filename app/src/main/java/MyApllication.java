import android.app.Application;

import ru.arturvasilov.sqlite.core.SQLite;

/**
 * Created by ahmed hamdy on 12/18/2017.
 */

public class MyApllication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SQLite.initialize(getApplicationContext());
    }
}
