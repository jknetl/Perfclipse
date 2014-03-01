package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.ui.gef.commands.AddValidatorCommand;

public class ValidatorListEditPolicy extends AbstractListEditPolicy {

	ValidationModel model;
	ScenarioModel parent;
	
	
	
	public ValidatorListEditPolicy(ValidationModel model, ScenarioModel parent) {
		super();
		this.model = model;
		this.parent = parent;
	}

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EditPart getInsertionReference(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object type = request.getNewObjectType();
		if (type == Validator.class){
			Validator validator = (Validator) request.getNewObject();
			if (model.getValidation() == null){
				model.createValidation();
				parent.setValidation(model.getValidation());
			}
			return new AddValidatorCommand(validator, model);
		}
		return null;
	}

}