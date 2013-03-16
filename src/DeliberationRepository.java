public interface DeliberationRepository {

	boolean exists(Deliberation deliberation) throws Exception;

	void save(Deliberation deliberation) throws Exception;

}
