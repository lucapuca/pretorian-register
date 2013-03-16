import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		String urlString = "http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=13060";
		PretorianRegister pretorianRegister = new PretorianRegister(urlString);
		DeliberationParser parser = new DeliberationParser(pretorianRegister);

		List<Deliberation> deliberations = parser.parse();
		for (Deliberation deliberation : deliberations) {
			System.out.println(deliberation.toString());
		}
	}
}
