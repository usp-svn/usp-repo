package net.webHMI.pli.client;


import java.util.ArrayList;
import java.util.List;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Pli implements IsWidget, EntryPoint {

	private PLIServiceAsync pliService;// = GWT.create(PLIService.class);

	private TextField customer;
	private TextField jobNumber;
	//private TextField grade;
	private TextField od;
	private TextField length;
	private TextField weight;
	private TextField currentWarehouse;
	private TextField currentBin;
	private TextField gauge;
	private TextField serialNumber;
	private FieldSet fieldSet1;
	private TextField partNumber;
	private FieldLabel nb;
	private VerticalLayoutContainer p2;
	private TextField newBin;
	private TextButton save;
	private ComboBox<Warehouse> combo;
	private CheckBox check1;
	private int pipenrstatus;
	private String wh;
	private VerticalLayoutContainer p;
	
	
	interface StateProperties extends PropertyAccess<Warehouse> {
		ModelKeyProvider<Warehouse> abbr();
		LabelProvider<Warehouse> desc();
		LabelProvider<Warehouse> name();
	}


	public Widget asWidget() {

		final BorderLayoutContainer con = new BorderLayoutContainer();
		//con.setBorders(true);

		FramedPanel panel = new FramedPanel();
		
		panel.setWidth(250);
		panel.setBodyStyle("background: none; padding: 10px");

	    p = new VerticalLayoutContainer();
		panel.add(p);

		serialNumber = new TextField();
		//		serialNumber.setAllowBlank(false);
		serialNumber.setEmptyText("Scan Serial Number...");
		serialNumber.addStyleName("login_input");

		serialNumber.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				//Info.display("Value Changed", "First name changed to " + event.getValue() == null ? "blank" : event.getValue());
				getPipedata(event.getValue());
				//getUser();
			}
		});
		p.add(new FieldLabel(serialNumber, "Serial Number"), new VerticalLayoutData(1, -1));

		FieldSet fieldSetdetails = new FieldSet();
		fieldSetdetails.setHeadingText("Pipe Details");
		fieldSetdetails.setCollapsible(false);
		p.add(fieldSetdetails);

		VerticalLayoutContainer details = new VerticalLayoutContainer();
		fieldSetdetails.add(details);

		customer = new TextField();
		//		customer.setAllowBlank(false);
		//		customer.setText("Customer ...");
		customer.addStyleName("login_input");
		//		customer.disable();
		//customer.setEnabled(false);
		customer.setReadOnly(true);
		//customer.setStyleName("fontSize","24px");
		details.add(new FieldLabel(customer, "Description"), new VerticalLayoutData(1, -1));

		jobNumber = new TextField();
		//		jobNumber.setAllowBlank(false);
		//		jobNumber.setText("Job Number ...");
		jobNumber.addStyleName("login_input");
		jobNumber.setReadOnly(true);
		details.add(new FieldLabel(jobNumber, "Job Number"), new VerticalLayoutData(1, -1));

		partNumber = new TextField();
		//		jobNumber.setAllowBlank(false);
		//		jobNumber.setText("Job Number ...");
		partNumber.addStyleName("login_input");
		partNumber.setReadOnly(true);
		details.add(new FieldLabel(partNumber, "Part Number"), new VerticalLayoutData(1, -1));


		//		grade = new TextField();
		//		//		grade.setAllowBlank(false);
		//		//		grade.setText("Grade ...");
		//		grade.addStyleName("login_input");
		//		grade.setReadOnly(true);
		//		details.add(new FieldLabel(grade, "Grade"), new VerticalLayoutData(1, -1));

		od = new TextField();
		//		od.setAllowBlank(false);
		//		od.setText("OD ...");
		od.addStyleName("login_input");
		od.setReadOnly(true);
		details.add(new FieldLabel(od, "Outside Diameter"), new VerticalLayoutData(1, -1));

		length = new TextField();
		//		length.setAllowBlank(false);
		//		length.setText("Length ...");
		length.addStyleName("login_input");
		length.setReadOnly(true);
		details.add(new FieldLabel(length, "Length"), new VerticalLayoutData(1, -1));

		gauge = new TextField();
		//		gauge.setAllowBlank(false);
		//		gauge.setText("OD ...");
		gauge.addStyleName("login_input");
		gauge.setReadOnly(true);
		details.add(new FieldLabel(gauge, "Gauge"), new VerticalLayoutData(1, -1));

		weight = new TextField();
		//		weight.setAllowBlank(false);
		//		weight.setText("Weight ....");
		weight.addStyleName("login_input");
		weight.setReadOnly(true);
		details.add(new FieldLabel(weight, "Weight"), new VerticalLayoutData(1, -1));

		FieldSet fieldSet = new FieldSet();
		fieldSet.setHeadingText("Current Location");
		fieldSet.setCollapsible(false);
		p.add(fieldSet);

		VerticalLayoutContainer p1 = new VerticalLayoutContainer();
		fieldSet.add(p1);

		currentWarehouse = new TextField();
		//		currentWarehouse.setAllowBlank(false);
		//		currentWarehouse.setText("Weight ....");
		currentWarehouse.addStyleName("login_input");
		currentWarehouse.setReadOnly(true);
		p1.add(new FieldLabel(currentWarehouse, "Warehouse"), new VerticalLayoutData(1, -1));

		currentBin = new TextField();
		//		currentBin.setAllowBlank(false);
		//		currentBin.setText("Weight ....");
		currentBin.addStyleName("login_input");
		currentBin.setReadOnly(true);
		p1.add(new FieldLabel(currentBin, "Bin"), new VerticalLayoutData(1, -1));

		fieldSet1 = new FieldSet();
		fieldSet1.setHeadingText("New Location");
//		fieldSet1.setCollapsible(true);
//		fieldSet1.collapse();



		ValueChangeHandler<Boolean> checkBoxHandler = new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				
				if (event.getValue()){
					//fieldSet1.expand();
					p.add(fieldSet1);
				}
				else{
					//fieldSet1.collapse();
					p.remove(fieldSet1);
				}

			}
		};



		HorizontalPanel hp = new HorizontalPanel();
		check1 = new CheckBox();
		check1.setEnabled(true);
		check1.setBoxLabel("Move To");
		check1.addValueChangeHandler(checkBoxHandler);
		hp.add(check1);

		p.add(check1, new VerticalLayoutData(1, -1));

		//p.add(fieldSet1);

		p2 = new VerticalLayoutContainer();
		fieldSet1.add(p2);


		final TextField newWarehouse = new TextField();
		newWarehouse.setAllowBlank(false);
		//		newWarehouse.setText("Weight ....");
		newWarehouse.setStyleName("style-font");
		newWarehouse.setReadOnly(true);
		//p2.add(new FieldLabel(newWarehouse, "Warehouse"), new VerticalLayoutData(1, -1));


		StateProperties props = GWT.create(StateProperties.class);
		ListStore<Warehouse> states = new ListStore<Warehouse>(props.abbr());
		states.addAll(getWarehouse());

		combo = new ComboBox<Warehouse>(states, props.name());
		addHandlersForEventObservation(combo, props.name());

		combo.setEmptyText("Select Warehouse...");
		combo.setWidth(250);
		combo.setTypeAhead(true);
		combo.setTriggerAction(TriggerAction.ALL);

		p2.add(new FieldLabel(combo, "Warehouse"), new VerticalLayoutData(1, -1));

		newBin = new TextField();
		newBin.setAllowBlank(false);
		newBin.setEmptyText("Scan Bin ....");
		newBin.addStyleName("login_input");
		newBin.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				//Info.display("Value Changed", "First name changed to " + event.getValue() == null ? "blank" : event.getValue());
				if(!event.getValue().isEmpty() && pipenrstatus==1 && wh.equalsIgnoreCase("FGQC")){
					save.setEnabled(true);
				}
			}
		});

		//final FieldLabel nw = new FieldLabel(newWarehouse, "Warehouse");
		nb = new FieldLabel(newBin, "Bin");
		p2.add(nb, new VerticalLayoutData(1, -1));
		//nb.setVisible(false);




		save =new TextButton("Save", new SelectHandler() {


			@Override
			public void onSelect(SelectEvent event) {

				updateDB(serialNumber.getValue(),newBin.getValue(),combo.getCurrentValue().getName());
				serialNumber.setValue("", false);
				customer.setText("");
				partNumber.setText("");
				od.setText("");
				length.setText("");
				jobNumber.setText("");
				currentWarehouse.setText("");
				gauge.setText("" );
				weight.setText("");
				currentBin.setText("" );
				//grade.setText("");
				newBin.setValue("", false);
				combo.clear();
				check1.clear();
				//fieldSet1.collapse();
				save.setEnabled(false);
				p.remove(fieldSet1);
			}


		});
		final TextButton clear = new TextButton("Clear", new SelectHandler() {


			@Override
			public void onSelect(SelectEvent event) {

				serialNumber.setValue("", false);
				customer.setText("");
				jobNumber.setText("");
				partNumber.setText("");
				od.setText("");
				length.setText("");
				gauge.setText("" );
				weight.setText("");
				currentBin.setText("" );
				//grade.setText("");
				newBin.setValue("", false);
				currentWarehouse.setText("");
				combo.clear();
				check1.clear();
				//fieldSet1.collapse();
				save.setEnabled(false);
				check1.setEnabled(true);
				p.remove(fieldSet1);
			}
		});
		save.setEnabled(false);

		panel.addButton(save);
		panel.addButton(clear);

		MarginData centerData = new MarginData();
		centerData.setMargins(new Margins(5, 5, 0, 5));
		con.setCenterWidget(panel, centerData);
		//		con.collapse(r);
		SimpleContainer simple = new SimpleContainer();//
		// screen margins
		simple.add(con, new MarginData(10,200,10,200));
		
		
		serialNumber.focus(); //does not work
		
		return simple;
	}

	private <T> void addHandlersForEventObservation(ComboBox<T> combo, final LabelProvider<T> labelProvider) {
		combo.addSelectionHandler(new SelectionHandler<T>() {
			@Override
			public void onSelection(SelectionEvent<T> event) {
				//Info.display("State Selected", "You selected "
				//          + (event.getSelectedItem() == null ? "nothing" : labelProvider.getLabel(event.getSelectedItem()) + "!"));
				if (labelProvider.getLabel(event.getSelectedItem()).toString().equalsIgnoreCase("FGQC")){
					//p2.add(new FieldLabel(nb, "Bin"), new VerticalLayoutData(1, -1));
					//Info.display("State Selected", "You selected "
					//        + (event.getSelectedItem() == null ? "nothing" : labelProvider.getLabel(event.getSelectedItem()) + "!"));
					wh = labelProvider.getLabel(event.getSelectedItem()).toString();
					nb.setVisible(true);
				}else
				{
					// p2.remove(nb);
					//nb.setVisible(false);
					save.setEnabled(false);
				}
			}
		});

	}

	public void onModuleLoad() {
		//rpc service, remote methods etc
		if (pliService == null) {
			pliService = GWT.create(PLIService.class);
		}
		Widget con = asWidget();
		Viewport viewport = new Viewport( );
		viewport.add(con);
		RootPanel.get().add(viewport);

	}
    // asynchronous rpc call to server 
	private void getPipedata(String pipeid) {
		currentBin.setText("" );
		//grade.setText("");
		newBin.setValue("", false);
		currentWarehouse.setText("");
		combo.clear();
		check1.clear();
		//fieldSet1.collapse();
		save.setEnabled(false);
		check1.setEnabled(true);
		
		AsyncCallback<PLIData> callback = new AsyncCallback<PLIData>() {
			public void onFailure(Throwable caught) {

				//serialNumber.setValue("", false);
				customer.setText("");
				jobNumber.setText("");
				partNumber.setText("");
				od.setText("");
				length.setText("");
				gauge.setText("" );
				weight.setText("");
				currentBin.setText("" );
				//grade.setText("");
				newBin.setValue("", false);
				currentWarehouse.setText("");
				combo.clear();
				check1.clear();
				//fieldSet1.collapse();
				save.setEnabled(false);
				check1.setEnabled(true);
			}
			@Override
			public void onSuccess(PLIData result) {
				if (result.getStatus()==1){
					customer.setText(result.getDescription());
					partNumber.setText("" + result.getPartNumber());
					jobNumber.setText("" + result.getJobnumber());
					od.setText("" + result.getOd());
					length.setText(""+ result.getLength());
					gauge.setText("" + result.getGauge());
					weight.setText(""+ result.getWeight());
					currentBin.setText("" + result.getCurrentBin() );
					//grade.setText(""+ result.getGrade());
					currentWarehouse.setText(""+ result.getCurrentWarehouse());
					pipenrstatus =1;
					if (    result.getCurrentWarehouse().equalsIgnoreCase("FGAB") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGAC") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGCB") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGCC") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGD") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGNAD") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGNB") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGNC") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGS") ||
							result.getCurrentWarehouse().equalsIgnoreCase("FGWW") 
						
						){
						
							check1.setEnabled(false);
							p.remove(fieldSet1);
							//fieldSet1.setCollapsible(false);
					}
					else{
						check1.setEnabled(true);
						//fieldSet1.setCollapsible(true);
					}
						
				}
				else{
					customer.setText("");
					jobNumber.setText("");
					partNumber.setText("");
					od.setText("");
					length.setText("");
					gauge.setText("" );
					weight.setText("");
					currentBin.setText("" );
					//grade.setText("");
					newBin.setValue("", false);
					currentWarehouse.setText("");
					combo.clear();
					check1.clear();
					//fieldSet1.collapse();
					save.setEnabled(false);
					Info.display("Error", "Pipe does not exist !");
					pipenrstatus =0;
					check1.setEnabled(true);
				}
			}

		};
		pliService.getData(pipeid, callback);

	}
	
	//update Database 
	private void updateDB(String pipenr, String binnum, String warehouscode) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Void result) {


			}

		};
		pliService.updateDB(pipenr,binnum,warehouscode, callback);

	}
	//	public void getUser(){
	//		AsyncCallback<String> callback = new AsyncCallback<String>() {
	//			public void onFailure(Throwable caught) {
	//
	//			}
	//
	//
	//			@Override
	//			public void onSuccess(String result) {
	//				customer.setText(result);
	//			}
	//
	//		};
	//		pliService.getUser( callback);
	//
	//	}

	public static List<Warehouse> getWarehouse() {
		List<Warehouse> warehouse = new ArrayList<Warehouse>();
		//warehouse.add(new Warehouse("FGAB", "FG API Pipe-Bare","FGAB"));
		//warehouse.add(new Warehouse("FGAC", "FG API Pipe-COated","FGAC"));
		//warehouse.add(new Warehouse("FGCB", "FG CSA Pipe-Bare","FGCB"));
		//warehouse.add(new Warehouse("FGCC", "FG CSA Pipe-Coated","FGCC"));
		//warehouse.add(new Warehouse("FGD", "FG API Downgrade","FGD"));
		//warehouse.add(new Warehouse("FGNAD", "FG Non API Downgrade","FGNAD"));
		//warehouse.add(new Warehouse("FGNB", "FG Non API Pipe-Bare","FGNB"));
		//warehouse.add(new Warehouse("FGNC", "FG Non API Pipe-Coated","FGNC"));
		warehouse.add(new Warehouse("FGQC", "FG Quality Control","FGQC"));
		//warehouse.add(new Warehouse("FGS", "FG API Stock","FGS"));
		//warehouse.add(new Warehouse("FGWW", "FG AWWA","FGWW"));
		return warehouse;
	}




}