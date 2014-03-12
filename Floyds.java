


/**
 * @author prashanth
 *
 */
public class Floyds {

	int a[][][], path[][][];
	String pathNodes;

	public void runShortestPath(RideInputData inputData) {

		int noOfNodes = inputData.getNoOfLocations();

		a = new int[2][noOfNodes][noOfNodes];
		path = new int[2][noOfNodes][noOfNodes];
		for (int i = 0; i < noOfNodes; i++) {
			for (int j = 0; j < noOfNodes; j++) {
				int temp = inputData.getLocDistances().get(i).get(j)
						.getInputDistance();

				if (i == j) {
					a[0][i][j] = 0;
					path[0][i][j] = -1;
					continue;
				}
				if (temp == -1) {
					a[0][i][j] = Constants.MAX_VAL;
					path[0][i][j] = -1;
				} else {
					a[0][i][j] = temp;
					path[0][i][j] = i;
				}
				// System.out.print(" " + a[0][i][j]);

			}
			// System.out.println();
		}

		for (int k = 0; k < noOfNodes; k++) {

			for (int i = 0; i < noOfNodes; i++)

				for (int j = 0; j < noOfNodes; j++) {

					if (k % 2 == 0) {

						if (a[0][i][j] > a[0][i][k] + a[0][k][j]) {
							a[1][i][j] = a[0][i][k] + a[0][k][j];
							path[1][i][j] = path[0][k][j];
						} else {
							a[1][i][j] = a[0][i][j];
							path[1][i][j] = path[0][i][j];
						}

					} else {
						if (a[1][i][j] > a[1][i][k] + a[1][k][j]) {
							a[0][i][j] = a[1][i][k] + a[1][k][j];
							// path[i][j] = k;
							path[0][i][j] = path[1][k][j];
						} else {
							a[0][i][j] = a[1][i][j];
							path[0][i][j] = path[1][i][j];
						}
					}
				}
		}
		int index = noOfNodes % 2;
		for (int i = 0; i < noOfNodes; i++) {
			for (int j = 0; j < noOfNodes; j++) {

				inputData.getLocDistances().get(i).get(j)
						.setShortDistance(a[index][i][j]);

				inputData.getLocDistances().get(i).get(j)
						.setPreviousNode(path[index][i][j]);

//				System.out.print(inputData.getLocDistances().get(i).get(j)
//						.getShortDistance()
//						+ "	");

//				if ((inputData.getLocDistances().get(i).get(j)
//						.getShortDistance() + "").toString().length() < 2) {
//					System.out.print(" ");
//				}

			}
//			System.out.println();
		}

//		for (int i = 0; i < noOfNodes; i++) {
//			for (int j = 0; j < noOfNodes; j++) {
//
//				System.out.print(" " + path[index][i][j]);
//
//			}
//			System.out.println();
//		}

	}

}
