package atg.siebel.configurator.command;


import atg.nucleus.logging.ApplicationLogging;
import atg.nucleus.logging.ClassLoggingFactory;
import atg.siebel.configurator.ConfigurationContext;
import atg.siebel.configurator.ConfiguratorException;
import atg.siebel.configurator.ConfigurationParams.ContextParameters;

import com.siebel.ordermanagement.configurator.cfginteractrequest.Request;

/**
 */
public class CreateProductCommand extends UpdateCommand {

	/** Logger */
	protected static ApplicationLogging mLogger = 
	    ClassLoggingFactory.getFactory().getLoggerForClass(CreateProductCommand.class);
    
    // The id of the relationship with parent
    protected String mRelationshipId;
    protected ConfigurationContext mEvent;

    public CreateProductCommand(ConfigurationContext pEvent) {
        super(pEvent);
        mEvent = pEvent;
        mRelationshipId = (String) getConfigurationParams().getParam(ContextParameters.RELATIONSHIP_ID);
    }



    @Override
    protected CommandResult doExecute() throws ConfiguratorException {

        if (mLogger.isLoggingDebug()) {
        	mLogger.logDebug("execute started");
        }

        AddProductCommand addProductCmd = new AddProductCommand(mEvent);
        CommandResult addResult = addProductCmd.execute();

//        if (!addResult.getStatus().isSuccessfull()) {
//            throw new ConfiguratorException("Adding of product was not successfull due to - "
//            		+ addResult.getStatus().getMessages());
//        }


        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // this will pass the parent and not the new instance!!!!
        SetAttributeCommand setAttributeCfgCmnd = new SetAttributeCommand(mEvent);
        CommandResult result = setAttributeCfgCmnd.execute();

//        if (!result.getStatus().isSuccessfull()) {
//        	// WE ALSO NEED TO ROLLBACK THE addProductCmd!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        	// CALL REMOVECFGCOMMAND ????
//        	throw new ConfiguratorException("Setting attributes was not successfull due to - "
//        			+ addResult.getStatus().getMessages());
//        }

        return result;
    }


    @Override
    protected Request createRequest() {
        throw new IllegalAccessError("This method forbidden");
    }

}
