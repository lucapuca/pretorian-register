public class Main {

	public static void main(String[] args) throws Exception {
		String urlString = "http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=13060";
		//PretorianRegister pretorianRegister = new PretorianRegister(urlString);
		DeliberationParser parser = new JSoupDeliberationParser(urlString);
		//DeliberationParser parser = new RegexpDeliberationParser(pretorianRegister);
		DeliberationRepository repository = new MongoDeliberationRepository();
		DeliberationNotifier notifier = new MailDeliberationNotifier();
		DeliberationMonitor monitor = new DeliberationMonitor(parser, repository, notifier);

		monitor.run();
	}
}
