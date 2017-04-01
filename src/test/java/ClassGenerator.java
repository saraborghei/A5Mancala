/**
 * Created by sarab on 3/25/2017.
 * Updated by sarab on 4/1/2017.
 */

import de.uniks.networkparser.graph.Cardinality;
import de.uniks.networkparser.graph.Clazz;
import de.uniks.networkparser.graph.DataType;
import de.uniks.networkparser.graph.Parameter;
import org.junit.Test;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.storyboards.Storyboard;

public class ClassGenerator {
    /**
     * @see <a href='../../../doc/SdmClassGeneration.html'>SdmClassGeneration.html</a>
     */
    @Test
    public void testSdmClassGeneration() {
      /* This file will generate that necessary classes and class diagram for the
       * Mancala game with Scenarios example in the Story Driven Modeling book
	   */

        Storyboard story = new Storyboard();

        story.markCodeStart();
        ClassModel model = new ClassModel();

        //============================================================
        // Generate class House
        Clazz houseClass = model.createClazz("House")
                .withAttribute("houseNumber", DataType.INT)
                .withAttribute("numPebbles", DataType.INT)
                .withAttribute("isStore", DataType.BOOLEAN)
                .withMethod("addOpposite", DataType.BOOLEAN, new Parameter(DataType.create("House"))).with("House")
                .withMethod("removeOpposite", DataType.BOOLEAN, new Parameter(DataType.create("House"))).with("House")
                .withMethod("takeOppositePebbles", DataType.VOID)
                .withMethod("redistributePebblesCounterclockwise", DataType.create("House"))
                .withMethod("setAsStore", DataType.VOID, new Parameter(DataType.create("Player")));

        //============================================================
        // Generate class Player
        Clazz playerClass = model.createClazz("Player")
                .withAttribute("name", DataType.STRING)
                .withAttribute("activePlayer", DataType.BOOLEAN)
                .withMethod("isActive", DataType.BOOLEAN);

        //============================================================
        // Generate class Board
        Clazz boardClass = model.createClazz("Board")
                .withAttribute("gameTitle", DataType.STRING)
                .withAttribute("housesPerPlayer", DataType.INT)
                .withAttribute("pebblesPerHouse", DataType.INT)
                .withMethod("createGame", DataType.create("Board"));

        //============================================================
        // Add associations
        houseClass.withBidirectional(houseClass, "successor", Cardinality.ONE, "predecessor", Cardinality.ONE);
        playerClass.withBidirectional(houseClass, "has", Cardinality.MANY, "player", Cardinality.ONE);
        boardClass.withBidirectional(playerClass, "players", Cardinality.MANY, "uses", Cardinality.ONE);

        story.addClassDiagram(model);

        //============================================================
        // Generate class source files.");

        model.removeAllGeneratedCode("src/test/java");

        model.withAuthorName("Borghei");
        story.markCodeStart();
        model.generate("src/main/java");

        story.addCode();
        story.dumpHTML();
    }
}