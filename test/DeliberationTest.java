import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DeliberationTest {

	@Test
	public void testFromString() {
		String deliberationString = "href='showdoc.aspx?procid=51171' title=\"Visualizza documento\">  TITOLO DELLA DELIBERA.</a> </td> <td class='0' title=\"S ZONA 2\" style=\"width: 200px; padding-left: 5px; white-space:nowrap\">                                                        S ZONA 2                                                    </td>                                                    <!--td class='0' style=\"width: 75px; padding-left: 5px; text-align:right\">                                                        05/03/2013                                                    </td-->                                                    <td class='0' style=\"width: 80px; padding-left: 5px; text-align:right\">                                                        N. 28 169212/2013 <br> del 05/03/2013</td>                                                    <td class='0' style=\"widt";
		Deliberation deliberation = new Deliberation(deliberationString);
		assertEquals("TITOLO DELLA DELIBERA.", deliberation.title());
		assertEquals(
				"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/showdoc.aspx?procid=51171",
				deliberation.link());
		assertEquals("N. 28 169212/2013 del 05/03/2013", deliberation.id());
	}
}
