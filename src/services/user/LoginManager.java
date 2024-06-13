package services.user;

import java.util.prefs.Preferences;

import entity.user.User;

public class LoginManager {

	private static final String ID = "0";
	private static final String NAME = "abc";
	private static final String PHONE = "123";
	private static final String ADDRESS = "abc";
	private static final String EMAIL = "abc@example.com";
//    private static final String address = null;

	// Lưu thông tin đăng nhập
	public static void saveLoginInfo(int id, String name, String phone, String address, String email) {
		Preferences prefs = Preferences.userRoot().node(LoginManager.class.getName());
		prefs.put(ID,  Integer.toString(id));
		prefs.put(NAME, name);
		prefs.put(PHONE, phone);
		prefs.put(ADDRESS, address);
		prefs.put(EMAIL, email);
	}

	// Kiểm tra xem đã lưu thông tin đăng nhập hay chưa
	public static boolean isLoginInfoSaved() {
		Preferences prefs = Preferences.userRoot().node(LoginManager.class.getName());
		return prefs.get(ID, null) != null && prefs.get(NAME, null) != null && prefs.get(PHONE, null) != null
				&& prefs.get(ADDRESS, null) != null && prefs.get(EMAIL, null) != null;
	}

	// Lấy thông tin đăng nhập đã lưu
	public static User getSavedLoginInfo() {
		Preferences prefs = Preferences.userRoot().node(LoginManager.class.getName());
		int id  = Integer.parseInt(prefs.get(ID, null));
		String name = prefs.get(NAME, null);
		String phone = prefs.get(PHONE, null);
		String address = prefs.get(ADDRESS, null);
		String email = prefs.get(EMAIL, null);
		return new User(id, name, phone, address, email);
	}

	// Xóa thông tin đăng nhập đã lưu
	public static void clearSavedLoginInfo() {
		Preferences prefs = Preferences.userRoot().node(LoginManager.class.getName());
		prefs.remove(ID);
		prefs.remove(NAME);
		prefs.remove(PHONE);
		prefs.remove(ADDRESS);
		prefs.remove(EMAIL);
		prefs.remove(NAME);
	}
}