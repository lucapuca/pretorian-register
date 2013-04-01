import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	private static final Map<String, String[]> default_urls_map;
	static {
		Map<String, String[]> aMap = new HashMap<String, String[]>();
		aMap.put(
				"dirigenziali",
				new String[] {
						"Determinazioni Dirigenziali",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=9050" });
		aMap.put(
				"giunta",
				new String[] {
						"Delibere di Giunta",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=9030" });
		aMap.put(
				"consiglio",
				new String[] {
						"Delibere di Consiglio",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=9040" });
		aMap.put(
				"vari",
				new String[] {
						"Documenti Vari",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=9060" });
		aMap.put(
				"matrimonio",
				new String[] {
						"Pubblicazioni di Matrimonio",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=9020" });
		aMap.put(
				"zona1",
				new String[] {
						"Delibere di Zona 1",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=12060" });
		aMap.put(
				"zona2",
				new String[] {
						"Delibere di Zona 2",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=13060" });
		aMap.put(
				"zona3",
				new String[] {
						"Delibere di Zona 3",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=14060" });
		aMap.put(
				"zona4",
				new String[] {
						"Delibere di Zona 4",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=15060" });
		aMap.put(
				"zona5",
				new String[] {
						"Delibere di Zona 5",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=16060" });
		aMap.put(
				"zona6",
				new String[] {
						"Delibere di Zona 6",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=17060" });
		aMap.put(
				"zona7",
				new String[] {
						"Delibere di Zona 7",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=18060" });
		aMap.put(
				"zona8",
				new String[] {
						"Delibere di Zona 8",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=19060" });
		aMap.put(
				"zona9",
				new String[] {
						"Delibere di Zona 9",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=20060" });
		aMap.put(
				"60",
				new String[] {
						"Articolo 60",
						"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=9070" });
		default_urls_map = Collections.unmodifiableMap(aMap);
	}

	private static String propertyGetString(Properties properties, String key,
			String def) {
		return properties.getProperty(key, def);
	}

	private static String propertyGetString(Properties properties, String key) {
		return propertyGetString(properties, key, "");
	}

	private static boolean propertyGetBoolean(Properties properties,
			String key, boolean def) {
		return Boolean.valueOf(propertyGetString(properties, key,
				Boolean.toString(def)));
	}

	private static int propertyGetInteger(Properties properties, String key,
			int def) {
		return Integer.valueOf(propertyGetString(properties, key,
				Integer.toString(def)));
	}

	private static Set<DeliberationMonitor> getEntryFromConf(
			Properties properties, String from, String pwd,
			String def_destination, int def_timeout) {
		Set<DeliberationMonitor> ret = new HashSet<DeliberationMonitor>();

		for (String n : default_urls_map.keySet()) {
			String[] def = default_urls_map.get(n);
			boolean enable = propertyGetBoolean(properties, n + ".enable",
					false);
			String dest = propertyGetString(properties, n + ".destination",
					def_destination);
			String subj = propertyGetString(properties, n + ".subject", "");
			String description = propertyGetString(properties, n
					+ ".description", def[0]);
			String url = propertyGetString(properties, n + ".url", def[1]);
			int timeout = propertyGetInteger(properties, n + ".timeout", def_timeout);
			if (enable) {
				try {
					MongoDeliberationRepository mdr = new MongoDeliberationRepository();
					DeliberationParser parser = new JSoupDeliberationParser(url,timeout);
					MailDeliberationNotifier notifier = MailDeliberationNotifier
							.getNotifier(dest);
					if (subj.length() > 0) {
						notifier.setSubject(subj);
					}
					ret.add(new DeliberationMonitor(n, description, parser,
							mdr, notifier));
					logger.info("[" + n + "] : " + description
							+ " --- Enable -> " + dest);
				} catch (Exception e) {
					logger.severe("Exception while enabling " + n);
					e.printStackTrace();
				}

			} else {
				logger.info("[" + n + "] : " + description + " --- Disable -> "
						+ dest);
			}
		}

		return ret;
	}

	private static void fixMailProperties(Properties mailProperties) {
		if (mailProperties.get("mail.smtp.auth") == null) {
			mailProperties.put("mail.smtp.auth", "true");
		}
		if (mailProperties.get("mail.smtp.starttls.enable") == null) {
			mailProperties.put("mail.smtp.starttls.enable", "true");
		}
		if (mailProperties.get("mail.smtp.host") == null) {
			mailProperties.put("mail.smtp.host", "smtp.gmail.com");
		}
		if (mailProperties.get("mail.smtp.port") == null) {
			mailProperties.put("mail.smtp.port", "587");
		}
	}

	public static void main(String[] args) {
		String cfg = "etc/config.properties";
		if (args.length > 0) {
			cfg = args[1];
		}
		Properties properties = new Properties();
		Set<DeliberationMonitor> monitors = null;

		try {
			properties.load(new FileInputStream(cfg));
			properties.get("mail.user");
			final String username = propertyGetString(properties, "mail.user");
			final String pwd = propertyGetString(properties, "mail.password");
			if (username.length() == 0 || pwd.length() == 0) {
				logger.severe("Username(mail.user) and Password(mail.password) must be configured");
				System.exit(1);
			}
			fixMailProperties(properties);

			MailDeliberationNotifier.setUpFactory(username, pwd, properties);

			final int def_timeout = propertyGetInteger(properties,
					"connection.timeout", 10000);
			
			final String def_destination = propertyGetString(properties,
					"mail.destination", "");
			monitors = getEntryFromConf(properties, username, pwd,
					def_destination, def_timeout);

		} catch (IOException e) {
			logger.severe("Cannot open " + cfg);
			System.exit(1);
		}

		for (final DeliberationMonitor m : monitors) {
			new Thread() {
				public void run() {
					try {
						logger.info("Started [" + m.getName() +"] : " + m.getDescription());
						m.run();
						logger.info("Done [" + m.getName() +"]");
					} catch (Exception e) {
						logger.severe("EXEC ERROR [" + m.getName() + "]"+ e.toString());
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}
