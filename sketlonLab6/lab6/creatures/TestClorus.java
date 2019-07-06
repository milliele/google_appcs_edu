package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals("clorus",c.name());
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus(2);
        Plip p = new Plip(2);
        c.attack(p);
        assertEquals(4,c.energy(),0.01);
    }

    @Test
    public void testReplicate() {
        Plip p = new Plip(2);
        Plip baby = p.replicate();
        assertNotSame(p,baby);
        assertEquals(1,p.energy(),0.01);
        assertEquals(1,baby.energy(),0.01);
    }

    /** Cloruss take exactly the following actions based on NEIGHBORS:
     *  1. If there are no empty squares, the Clorus will STAY (even if there are Plips nearby they could attack).
     *  2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     *  3. Otherwise, if the Clorus has energy greater than or equal to one, it will REPLICATE to a random empty square.
     *  4. Otherwise, the Clorus will MOVE to a random empty square.
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    @Test
    public void testChoose() {
        Clorus c = new Clorus(1.00);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());
        assertEquals(c.chooseAction(surrounded), new Action(Action.ActionType.STAY));

        surrounded.put(Direction.BOTTOM,new Plip());
        assertEquals(c.chooseAction(surrounded), new Action(Action.ActionType.STAY));

        surrounded.put(Direction.TOP, new Empty());
        assertEquals(c.chooseAction(surrounded), new Action(Action.ActionType.ATTACK,Direction.BOTTOM));

        surrounded.put(Direction.BOTTOM,new Impassible());
        assertEquals(c.chooseAction(surrounded), new Action(Action.ActionType.REPLICATE,Direction.TOP));

        c.move();
        assertEquals(c.chooseAction(surrounded), new Action(Action.ActionType.MOVE,Direction.TOP));

    }


    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
}
