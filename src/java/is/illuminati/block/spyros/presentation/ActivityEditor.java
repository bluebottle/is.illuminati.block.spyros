package is.illuminati.block.spyros.presentation;

import is.illuminati.block.spyros.SpyrosConstants;

import javax.faces.context.FacesContext;

import com.idega.event.IWPageEventListener;
import com.idega.facelets.ui.FaceletComponent;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWException;
import com.idega.presentation.IWBaseComponent;
import com.idega.presentation.IWContext;

public class ActivityEditor extends IWBaseComponent implements IWPageEventListener {

	private IWBundle iwb;

	@Override
	protected void initializeComponent(FacesContext context) {
		IWContext iwc = IWContext.getIWContext(context);
		iwb = getBundle(context, SpyrosConstants.IW_BUNDLE_IDENTIFIER);
		
		FaceletComponent facelet = (FaceletComponent) iwc.getApplication().createComponent(FaceletComponent.COMPONENT_TYPE);
		facelet.setFaceletURI(iwb.getFaceletURI("activityEditor/editor.xhtml"));
		add(facelet);
	}

	@Override
	public boolean actionPerformed(IWContext arg0) throws IWException {
		return false;
	}

}