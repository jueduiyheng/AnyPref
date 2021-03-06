package net.nashlegend.anypref;

import android.os.Build;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by NashLegend on 16/6/7.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class SharedPrefsTest {

    @Before
    public void setUp() throws Exception {
        AnyPref.init(RuntimeEnvironment.application);
    }

    @Test
    public void testNoTransaction() {
        SharedPrefs prefs = AnyPref.getPrefs("test");
        prefs.putBoolean("bool", true);
        Assert.assertEquals(true, prefs.getBoolean("bool", false));
        prefs.putInt("int", 1);
        Assert.assertEquals(1, prefs.getInt("int", 0));
        prefs.putFloat("float", 2);
        Assert.assertEquals(2, prefs.getFloat("float", 0), 0.0001);
        prefs.putLong("long", 3);
        Assert.assertEquals(3, prefs.getLong("long", 0));
        prefs.putString("string", "str");
        Assert.assertEquals("str", prefs.getString("string", ""));
    }


    @Test
    public void testTransaction() {
        SharedPrefs prefs = AnyPref.getPrefs("test");
        prefs.beginTransaction()
                .putBoolean("bool", true)
                .putInt("int", 1)
                .putFloat("float", 2)
                .putLong("long", 3)
                .putString("string", "str")
                .commit();
        Assert.assertEquals(true, prefs.getBoolean("bool", false));
        Assert.assertEquals(1, prefs.getInt("int", 0));
        Assert.assertEquals(2, prefs.getFloat("float", 0), 0.0001);
        Assert.assertEquals(3, prefs.getLong("long", 0));
        Assert.assertEquals("str", prefs.getString("string", ""));
    }

    @Test
    public void testContains() {
        SharedPrefs prefs = AnyPref.getPrefs("test");
        Assert.assertFalse(prefs.contains("random"));
        prefs.putLong("random",1);
        Assert.assertTrue(prefs.contains("random"));
        prefs.remove("random");
        Assert.assertFalse(prefs.contains("random"));
    }

    @Test
    public void testClear() {
        SharedPrefs prefs = AnyPref.getPrefs("test");

        prefs.putLong("me",222);

        prefs.beginTransaction().clear().putInt("you",33).commit();

        Assert.assertFalse(prefs.contains("me"));
        Assert.assertTrue(prefs.contains("you"));

    }
}