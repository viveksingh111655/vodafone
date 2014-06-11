package atg.siebel.catalog.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.transaction.TransactionManager;

import atg.nucleus.logging.VariableArgumentApplicationLogging;
import atg.repository.Repository;
import atg.siebel.catalog.job.ImportJobManager;

public interface ImportServiceInterface extends VariableArgumentApplicationLogging{

  public abstract TransactionManager getTransactionManager();

  public abstract void setTransactionManager(
      TransactionManager pTransactionManager);

  public abstract String getImportFileLocation();

  public abstract void setImportFileLocation(String pImportFileLocation);

  public abstract boolean isCanDisableIndexes();

  public abstract void setCanDisableIndexes(boolean pCanDisableIndexes);

  public abstract String getImportDirectory();

  public abstract void setImportDirectory(String pImportDirectory);

  public abstract File getLogFilePath();

  public abstract void setLogFilePath(File pLogFilePath);

  public abstract boolean isUseDASImportTool();

  public abstract void setUseDASImportTool(boolean pUseDASImportTool);

  public abstract String getExportJobId();

  public abstract void setExportJobId(String pExportJobId);

  public abstract ImportJobManager getJobManager();

  public abstract void setJobManager(ImportJobManager pJobManager);

  public abstract Repository getTargetRepository(String pTargetRepository);

  public abstract Map<String, Repository> getTargetRepositories();

  public abstract void setTargetRepositories(
      Map<String, Repository> pRepositories);

  public abstract ImportSession getImportSession();

  public abstract String getServiceStatus();

  public abstract ReferenceItemGenerator getReferenceItemGenerator();

  public abstract ArrayList<ImportItem> getFailedDeletions();

  public abstract void setDataImportErrors();

  // -------------------------------------
  /**
   * This is the main execution method for the service. This method sets the
   * security context on the thread for the user specified in the userName
   * property. Next, it creates a project and then calls importData(). Next, it
   * attempts to advance the project's workflow. Finally, it unsets the security
   * context.
   */
  public abstract boolean executeImport() throws Exception;

  public abstract void notifyThreadStatus(int pThreadIndex, String pName,
      int pStatus);

  public abstract void processFailedDeletions();

  public abstract void logBatchFailure(int pSegmentIndex, int pPhase);

  public abstract void logFailedItem(ImportFailedItem pFailedItem, int pPhase);

  public abstract void logFailureMessage(String pMessage);

  /**
   * endImportSession
   * 
   * This method sets ends the import session by completing the current
   * transaction and ending the publishing workflow session in the case of
   * import into a versioned repository.
   * 
   * This method must be called at the end of an import session
   * 
   * @param pSession
   *          - the import session.
   */

  public abstract void endImportSession() throws ImportException;

  // -------------------------------------
  /**
   * This method cancels the import.
   * 
   * @throws ImportException
   */
  public abstract void cancelImport() throws ImportException;

  public abstract Object resolveName(String repositoryPath);
  
  public boolean disableIndexes();
  
  public boolean importData() throws ImportException;

  public boolean rebuildIndexes();
}