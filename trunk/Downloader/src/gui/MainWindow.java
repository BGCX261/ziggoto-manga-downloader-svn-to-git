package gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import persistence.MangaDAO;
import persistence.VolumeDAO;
import beans.Manga;
import beans.Volume;

/**
 *
 * @author Fabio
 */
public class MainWindow extends JFrame {
    // Variables declaration - do not modify                     
    private JButton btnSubmeter;
    private JCheckBox checkIsTodos;
    private JComboBox listaTitulos;
    private JList listaMangas;
    private JScrollPane jScrollPane1;
    // End of variables declaration   
    
    private ListaMangaModel lmm = new ListaMangaModel();
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
    	MangaDAO.getConnection(); //Realiza conexão com BD...
    	setupLookAndFeel();
    	initComponents();
        
        this.setTitle("...");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
        
    private List<Manga> getMangas(String volume){
    	return null;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        listaTitulos = new JComboBox();
        jScrollPane1 = new JScrollPane();
        listaMangas = new JList();
        checkIsTodos = new JCheckBox();
        btnSubmeter = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        listaTitulos.setModel(new ListaVolumesModel());
        listaTitulos.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					Volume v = (Volume) e.getItem();
					
					lmm.setVolume(v.getId());
					System.out.println(lmm.getLista());
					
					listaMangas.repaint(listaMangas.getBounds()); //Parte fundamental
				}
			}
			
		});

        checkIsTodos.setText("Selecionar todos");
        checkIsTodos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	boolean checkTodos = checkIsTodos.isSelected() ? true:false;
                selecionaTodos(evt, checkTodos);
            }
        });
        
        listaMangas.setModel(lmm);
        
        listaMangas.setCellRenderer(new CheckListRenderer());
        listaMangas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaMangas.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent event)
            {
              selectItem(event.getPoint());
            }
          });

        
        jScrollPane1.setViewportView(listaMangas);

        btnSubmeter.setText("Fazer Download");

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(10)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(listaTitulos, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createParallelGroup(Alignment.LEADING)
        					.addComponent(checkIsTodos)
        					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(18, Short.MAX_VALUE))
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addContainerGap(139, Short.MAX_VALUE)
        			.addComponent(btnSubmeter)
        			.addGap(101))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(26)
        			.addComponent(listaTitulos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(11)
        			.addComponent(checkIsTodos)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(btnSubmeter)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);

        pack();
    }
    // ------ FIM DO INIT() -----
    
    private void selecionaTodos(ActionEvent evt, boolean checkTodos) {
    	int index = 0, tamanho = listaMangas.getModel().getSize();
        
        while(index < tamanho){
        	CheckListItem item = (CheckListItem)listaMangas.getModel().getElementAt(index);
        	item.setSelected(checkTodos);
        	listaMangas.repaint(listaMangas.getCellBounds(index, index));
        	index++;
        }
    }
    
    private void selectItem(Point point)
    {
      int index = listaMangas.locationToIndex(point);

      if (index >= 0)
      {
        CheckListItem item = (CheckListItem)listaMangas.getModel().getElementAt(index);
        item.setSelected(!item.isSelected());
        listaMangas.repaint(listaMangas.getCellBounds(index, index));
      }
    }
    
    private void setupLookAndFeel(){
//    	try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
    }
}
// ====== FIM DA CLASSE PRINCIPAL =======

class CheckListItem
{
	private Manga manga;
	private boolean isSelected = false;
	
	public CheckListItem(Manga manga)
	{
		this.manga = manga;
	}

	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}

	public String toString()
	{
		return manga.getEdicaoFinal();
	}
}

//O segredo tá no "extends JCheckBox"
class CheckListRenderer extends JCheckBox implements ListCellRenderer
{
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus)
	{
		setEnabled(list.isEnabled());
		setSelected(((CheckListItem)value).isSelected());
		setFont(list.getFont());
		setBackground(list.getBackground());
		setForeground(list.getForeground());
		setText(value.toString());
		return this;
	}
} 

//Models

class ListaVolumesModel extends AbstractListModel implements ComboBoxModel{
	private static final long serialVersionUID = 1L;

	List<Volume> volumes = VolumeDAO.getTodosVolumes();
	Volume selecionado;
	
	@Override
	public Object getElementAt(int index) {
		return volumes.get(index);
	}

	@Override
	public int getSize() {
		return volumes.size();
	}

	@Override
	public Object getSelectedItem() {
		return selecionado;
	}

	@Override
	public void setSelectedItem(Object obj) {
		selecionado = (Volume) obj;
	}
	
}

class ListaMangaModel extends AbstractListModel {
    List<CheckListItem> checkboxes = new LinkedList<CheckListItem>();

// /* Só pra manter backup */    
//    public ListaMangaModel() {
//    	checkboxes.add(new CheckListItem(new Manga()));
//    	checkboxes.add(new CheckListItem(new Manga()));
//    	checkboxes.add(new CheckListItem(new Manga()));
//    	checkboxes.add(new CheckListItem(new Manga()));
//    }
    
    public List<CheckListItem> getLista(){
    	return checkboxes;
    }
    
    public void setVolume(int idVolume){
    	checkboxes = new LinkedList<CheckListItem>();
    	List<Manga> mangas = MangaDAO.getTodosMangasByIdVolume(idVolume);
    	
    	for(Manga m:mangas){
    		CheckListItem c = new CheckListItem(m);
    		checkboxes.add(c);
    	}
    }
	
    public int getSize() { return checkboxes.size(); }
    public Object getElementAt(int i) { return checkboxes.get(i); }
}
