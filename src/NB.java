
public class NB {
	private char[][][] image;
	private int[] label;
	
	private double p_y_true[];
	private double p_y_false[];
	
	public NB(char[][][] image, int[] label) {
		this.image = image;
		this.label = label;
		p_y_true = new double[label.length];
		p_y_false = new double[label.length];
		train();
	}
	
	
	public void train() {
		int count = 0;	
		int max = 0;
		/**
		 * loop to set up the size of possible outcomes (distinguish between digit and face) 
		 */
		for(int m = 0 ; m < label.length; m++) {
			if(max < label[m]) {
				max = label[m];
			}
		}
		/**
		 * loop to calculate p(y) for true and false 
		 */
		for(int i =0 ; i<max ; i++) {
			for(int j = 0 ; j < label.length; j++) {
				 if(label[j] == 0 ) {
					 count++;
				 }
			}
			p_y_true[i] = count/label.length;
			p_y_false[i] = (label.length-count) / label.length;
			count = 0;
		}
	
		/**
		 * 
		 */
		
	}
	
	
	
}
