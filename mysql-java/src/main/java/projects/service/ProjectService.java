package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

/*
 * 
The service layer in this small application is implemented by a single file, ProjectService.java.
Mostly this file acts as a pass-through between the main application file that runs the menu
(ProjectsApp.java) and the DAO file in the data layer (ProjectDao.java).

 * 
 */
public class ProjectService {
	
	//instance variable of RecipeiDao. Access the DAO from the service
	private ProjectDao projectDao = new ProjectDao();

	public Project addProject(Project project) {

		return projectDao.insertProject(project);
	}

	
}
