package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {

	  private ProjectService projectService = new ProjectService();

	
	/*
	 * To prevent the Eclipse formatter from reformatting the list, surround the variable declaration with
	 * // @formatter:off and // @formatter:on 
	 * This list of operations will be printed on the console so that the user will be reminded which selection to make
	 */
	
	  /* This is the list of available operations */
	// @formatter: off
	private List<String> operations = List.of( 
			"1) Add a project"
	);
	// @formatter:on
	
/*
 * This will set the scanner so that it accepts user input from the Java console
 */
	private Scanner scanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {

		new ProjectsApp().processUserSelections();
		
	}// end of main

/*
 * This method displays the menu selections, gets a selection from the user, and then acts on the selection
 */

	private void processUserSelections() {
		boolean done = false;

		while (!done) {
			try {
					int selection = getUserSelection();
					
			        switch (selection) {
			          case -1:
			            done = exitMenu();
			            break;

			          case 1:
			        	  createProject();
			            break;
/*
			          case 2:
			            addRecipe();
			            break;
*/

			          default:
			            System.out.println("\n" + selection + " is not valid. Try again.");
			            break;
			        }
			}
			catch (Exception e) {
				System.out.println("\nError: " + e + "Try again.");
			}
	    
		}//end of while 
		
	}

		  private void createProject() {
			  
			  String projectName = getStringInput("Enter the project name");
			  BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours"); 
			  BigDecimal actualHours = getDecimalInput("Enter the actual hours");
			  Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
			  //check is there was an input
			  //boolean isNumber = Objects.isNull(difficulty);
			  String notes = getStringInput("Enter the project notes");
			  
			  Project project = new Project();
			  
			  project.setProjectName (projectName); 
			  project.setEstimatedHours (estimatedHours); 
			  project.setActualHours (actualHours); 
			  project.setDifficulty (difficulty); 
			  project.setNotes (notes);
			  
			  Project dbProject = projectService.addProject(project);
			  System.out.println("You have successfully created project: " + dbProject);
			  
	}

		private BigDecimal getDecimalInput(String prompt) {
			
		    String input = getStringInput(prompt);
		    //if the input is a null we handle it
		    if (Objects.isNull(input)) {
		      return null;
		    }
		    
		    try {
		    	//if the input is not null and convert to integer
		    	return new BigDecimal(input).setScale(2);
		    } catch (NumberFormatException e) {
		      throw new DbException(input + " is not a valid decimal.");
		    }
			
		}

		/**
		   * This is called when the user is exiting the menu. It simply returns
		   * {@code true}, which will cause the menu to exit. This is not the best
		   * approach, as there is no guarantee what the caller will do with the return
		   * value. It does, however, keep the switch statement in the menu code
		   * cleaner.
		   * 
		   * @return {@code true} to cause the menu to exit.
		   */
		  private boolean exitMenu() {
		    System.out.println("\nExiting the menu. TTFN!");
		    return true;
		  }

	private int getUserSelection() {
		
		printOperations();
		Integer input = getIntInput("Enter a menu selection");
	    return Objects.isNull(input) ? -1 : input;

	}

	
	  private Integer getIntInput(String prompt) {
		    String input = getStringInput(prompt);
		    //if the input is a null we handle it
		    if (Objects.isNull(input)) {
		      return null;
		    }
		    
		    try {
		    	//if the input is not null and convert to integer
		      return Integer.parseInt(input);
		    } catch (NumberFormatException e) {
		      throw new DbException(input + " is not a valid number.");
		    }
	}

	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null: input.trim(); 
	}

	/**
	   * Print the available menu operations to the console, with each operation on
	   * a separate line.
	   */	
	private void printOperations() {
	    System.out.println();
	    System.out.println("Here's what you can do:");

	    /*
	     * Every List has a forEach method. The forEach method takes a Consumer as a
	     * parameter. This uses a Lambda expression to supply the Consumer. Consumer
	     * is a functional interface that has a single abstract method accept. The
	     * accept method returns nothing (void) and accepts a single parameter. This
	     * Lambda expression returns nothing (System.out.println has a void return
	     * value). It has a single parameter (op), which meets the requirements of
	     * Consumer.accept().
	     */
	    //Print our operations
	    operations.forEach(op -> System.out.println("   " + op));		
	}// end of processUserSelections
	
	

}// end of class ProjectsApp
