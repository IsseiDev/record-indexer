package client.searchGUI;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.swing.*;

import shared.communication.GetFields_Result;
import shared.communication.GetFields_Result.Project_id;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame {
	FrameController controller;
	
    // Declare private data here
    
    private JTextField searchField;
    private JList<String> fieldsList;
    private JScrollPane fieldsPane;
    private JList<String> imagesList;
    private JScrollPane imagesPane;
    private Vector<String> projects;
    private Vector<String> fields = new Vector<String>();
    private Vector<String> images = new Vector<String>();
    private ProjectListListener button1;
    private JComboBox<String> projectsList;
    private GetFields_Result fieldsResult;
	private SearchButtonListener button2;
	private Desktop d = Desktop.getDesktop();

    
	public SearchFrame(FrameController controller) {

		this.controller = controller;
		
		// Set the window's title
		this.setTitle("Search");
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = this.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);
        
        //The combo box will contain all the Projects
        projects = controller.getProjects();

        
		button1 = new ProjectListListener(this);
        projectsList = new JComboBox<String>(projects);
        
        //Listening to the combo box
        projectsList.addActionListener(button1);

        projectsList.setBounds(10, 10, 240, 20);
        this.add(projectsList);
        
        fieldsResult = controller.getFields(projectsList.getSelectedIndex()+1);
        setFields(fieldsResult);
       
        searchField = new JTextField("Search...");
        searchField.setBounds(10, 40, 240, 20);
        this.add(searchField);

        fieldsList = new JList<String>(fields);
        fieldsPane = new JScrollPane(fieldsList);
        fieldsPane.setBounds(10, 70, 240, 300);
        this.add(fieldsPane);
        
        imagesList = new JList<String>(images);

        imagesPane = new JScrollPane(imagesList);
        imagesPane.setBounds(280, 10, 380, 400);
        
        //Listening to the image list selector
        
        this.add(imagesPane);
        
		//Setting the button and the listener to the button
		button2 = new SearchButtonListener(this);
		JButton theButton = new JButton("Search");
		theButton.setBounds(10, 390, 240, 20);
		this.add(theButton);
		theButton.addActionListener(button2);
        
		// Set the location of the window on the desktop
		this.setLocation(100, 100);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.setSize(700, 500);
    }
	
	public Vector<String> getFields()
	{
		//Put in the correct project ID
		fieldsResult = controller.getFields(projectsList.getSelectedIndex()+1);
		setFields(fieldsResult);
		return fields;
	}
	
	public Vector<String> Search()
	{
		return controller.Search(setSelectedFieldsToString(), searchField.getText());
	}
	
	public void setFields(GetFields_Result fieldsResult)
	{
		fields.clear();
		for(Project_id p : fieldsResult.getProjects())
		{
			fields.add(p.getField_title());
		}
	}
	
	public void openImage()
	{
		try {
			String value = imagesList.getSelectedValue();
			d.browse(new URI(value));
		} catch (IOException | URISyntaxException e) {
			System.out.println("Could not open image: " + e);
		}
	}
	
	public String setSelectedFieldsToString()
	{
		int[] selected = fieldsList.getSelectedIndices();
		StringBuilder sb = new StringBuilder();
		
		int index=0;
		for(int i : selected)
		{
			if(index != selected.length-1)
			{
				sb.append(fieldsResult.getProjects().get(i).getField_id() + ",");
			}
			else
			{
				sb.append(fieldsResult.getProjects().get(i).getField_id());
			}
			index++;
		}
		
		return sb.toString();
	}

	/**
	 * @return the controller
	 */
	public FrameController getController() {
		return controller;
	}
	
	/**
	 * @return the controller
	 */
	public Vector<String> getField() {
		return fields;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(FrameController controller) {
		this.controller = controller;
	}

	/**
	 * @return the searchField
	 */
	public JTextField getSearchField() {
		return searchField;
	}

	/**
	 * @param searchField the searchField to set
	 */
	public void setSearchField(JTextField searchField) {
		this.searchField = searchField;
	}

	/**
	 * @return the fieldsList
	 */
	public JList<String> getFieldsList() {
		return fieldsList;
	}

	/**
	 * @param fieldsList the fieldsList to set
	 */
	public void setFieldsList(JList<String> fieldsList) {
		this.fieldsList = fieldsList;
	}

	/**
	 * @return the fieldsPane
	 */
	public JScrollPane getFieldsPane() {
		return fieldsPane;
	}

	/**
	 * @param fieldsPane the fieldsPane to set
	 */
	public void setFieldsPane(JScrollPane fieldsPane) {
		this.fieldsPane = fieldsPane;
	}

	/**
	 * @return the imagesList
	 */
	public JList<String> getImagesList() {
		return imagesList;
	}

	/**
	 * @param imagesList the imagesList to set
	 */
	public void setImagesList(JList<String> imagesList) {
		this.imagesList = imagesList;
	}

	/**
	 * @return the imagesPane
	 */
	public JScrollPane getImagesPane() {
		return imagesPane;
	}

	/**
	 * @param imagesPane the imagesPane to set
	 */
	public void setImagesPane(JScrollPane imagesPane) {
		this.imagesPane = imagesPane;
	}

	/**
	 * @return the projects
	 */
	public Vector<String> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(Vector<String> projects) {
		this.projects = projects;
	}

	/**
	 * @return the button
	 */
	public ProjectListListener getButton() {
		return button1;
	}

	/**
	 * @param button the button to set
	 */
	public void setButton(ProjectListListener button) {
		this.button1 = button;
	}

	/**
	 * @return the projectsList
	 */
	public JComboBox<String> getProjectsList() {
		return projectsList;
	}

	/**
	 * @param projectsList the projectsList to set
	 */
	public void setProjectsList(JComboBox<String> projectsList) {
		this.projectsList = projectsList;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(Vector<String> fields) {
		this.fields = fields;
	}

	/**
	 * @return the images
	 */
	public Vector<String> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(Vector<String> images) {
		this.images = images;
	}

	/**
	 * @return the fieldsResult
	 */
	public GetFields_Result getFieldsResult() {
		return fieldsResult;
	}

	/**
	 * @param fieldsResult the fieldsResult to set
	 */
	public void setFieldsResult(GetFields_Result fieldsResult) {
		this.fieldsResult = fieldsResult;
	}

	/**
	 * @return the button2
	 */
	public SearchButtonListener getButton2() {
		return button2;
	}

	/**
	 * @return the d
	 */
	public Desktop getD() {
		return d;
	}

	/**
	 * @param d the d to set
	 */
	public void setD(Desktop d) {
		this.d = d;
	}

	/**
	 * @param button2 the button2 to set
	 */
	public void setButton2(SearchButtonListener button2) {
		this.button2 = button2;
	}
}
