package projects.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.time.LocalTime;
//import java.util.List;

import projects.entity.Project;
import projects.exception.DbException;
import provided.util.DaoBase;

public class ProjectDao extends DaoBase {
	
	//private static final String CATEGORY_TABLE = "category";
	//private static final String MATERIAL_TABLE = "material"; 
	private static final String PROJECT_TABLE = "project";
	//private static final String PROJECT_CATEGORY_TABLE = "project_category"; 
	//private static final String STEP_TABLE = "step";

	  /**
	   * Insert a Project into the recipe table. This uses a
	   * {@link PreparedStatement} so that typed parameters can be passed into the
	   * statement, allowing validation and safety checks to be performed. This will
	   * mitigate against SQLInjection attacks.
	   * 
	   * @param recipe The recipe to be inserted.
	   * @return The recipe with the primary key value.
	   */
	  public Project insertProject(Project project) {
	    /*
	     * Note that the primary key (recipe_id) is not included in the list of
	     * fields in the insert statement. MySQL will set the correct primary key
	     * value when the row is inserted.
	     */
	    // @formatter:off
	    String sql = ""
	        + "INSERT INTO " + PROJECT_TABLE + " "
	        + "(project_name, estimated_hours, actual_hours, difficulty, notes) "
	        + "VALUES "
	        + "(?, ?, ?, ?, ?)";
	    // @formatter:on

	    try (Connection conn = DbConnection.getConnection()) {
	      startTransaction(conn);

	      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        setParameter(stmt, 1, project.getProjectName(), String.class);
	        setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
	        setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
	        setParameter(stmt, 4, project.getDifficulty(), Integer.class);
	        setParameter(stmt, 5, project.getNotes(), String.class);

	        /*
	         * Insert the row. Statement.executeUpdate() performs inserts,
	         * deletions, and modifications. It does all operations that do not
	         * return result sets.
	         */
	        stmt.executeUpdate();

	        /*
	         * Call a method in the base class to get the last insert ID (primary
	         * key value) in the given table.
	         */
	        Integer projectId = getLastInsertId(conn, PROJECT_TABLE);

	        commitTransaction(conn);

	        /*
	         * Set the recipe ID primary key to the value obtained by
	         * getLastInsertId(). This does not fill in the createdAt field. To get
	         * that value we would need to do a fetch on the recipe row.
	         */
	        project.setProjectId(projectId);
	        return project;
	      } catch (Exception e) {
	        rollbackTransaction(conn);
	        throw new DbException(e);
	      }
	    } catch (SQLException e) {
	      throw new DbException(e);
	    }
	  }

	
	
}
