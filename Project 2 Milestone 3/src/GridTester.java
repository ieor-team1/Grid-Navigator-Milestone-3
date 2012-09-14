
public class GridTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GridNavigator grid = new GridNavigator();
		ButtonCounter counter = new ButtonCounter();
		grid.calibrate();
		while(true){
//		System.out.println("current position: " + grid.position);	
		counter.count("Destination: " );
		grid.goTo(counter.get());
		}
	}

}
