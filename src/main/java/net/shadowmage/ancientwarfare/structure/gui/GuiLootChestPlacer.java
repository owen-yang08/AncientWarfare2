package net.shadowmage.ancientwarfare.structure.gui;

import net.minecraft.util.ResourceLocation;
import net.shadowmage.ancientwarfare.core.container.ContainerBase;
import net.shadowmage.ancientwarfare.core.gui.GuiContainerBase;
import net.shadowmage.ancientwarfare.core.gui.Listener;
import net.shadowmage.ancientwarfare.core.gui.elements.Button;
import net.shadowmage.ancientwarfare.core.gui.elements.CompositeScrolled;
import net.shadowmage.ancientwarfare.core.gui.elements.GuiElement;
import net.shadowmage.ancientwarfare.core.gui.elements.Label;
import net.shadowmage.ancientwarfare.core.gui.elements.Text;
import net.shadowmage.ancientwarfare.structure.container.ContainerLootChestPlacer;

import java.util.Comparator;
import java.util.stream.Collectors;

public class GuiLootChestPlacer extends GuiContainerBase<ContainerLootChestPlacer> {
	private static final int TOP_HEIGHT = 42;
	private static final int FORM_WIDTH = 300;
	private static final int FORM_HEIGHT = 200;

	private CompositeScrolled selectionArea;
	private Label selection;
	private Text filterInput;

	public GuiLootChestPlacer(ContainerBase container) {
		super(container, FORM_WIDTH, FORM_HEIGHT);
	}

	@Override
	public void initElements() {
		addGuiElement(new Label(8, 8, "guistrings.current_selection"));

		selection = new Label(8, 20, getSelectedLootTable());
		addGuiElement(selection);

		filterInput = new Text(8, 18 + 12, FORM_WIDTH - 16, "", this) {
			//kind of dirty...should possibly implement a real onCharEntered callback for when input actually changes
			@Override
			protected void handleKeyInput(int keyCode, char ch) {
				super.handleKeyInput(keyCode, ch);
				refreshGui();
			}
		};
		addGuiElement(filterInput);

		selectionArea = new CompositeScrolled(this, 0, TOP_HEIGHT, FORM_WIDTH, FORM_HEIGHT - TOP_HEIGHT);
		addGuiElement(selectionArea);
	}

	private String getSelectedLootTable() {
		return getContainer().getLootTable().map(ResourceLocation::toString).orElse("guistrings.none");
	}

	@Override
	public void setupElements() {
		int totalHeight = 8;

		selectionArea.clearElements();
		for (String lootTableName : getContainer().getLootTableNames().stream().filter(lt -> lt.toLowerCase().contains(filterInput.getText().toLowerCase()))
				.sorted(Comparator.naturalOrder()).collect(Collectors.toList())) {
			Button button = new Button(8, totalHeight, 272, 12, lootTableName);
			button.addNewListener(new Listener(Listener.MOUSE_UP) {
				@Override
				public boolean onEvent(GuiElement widget, ActivationEvent evt) {
					if (evt.mButton == 0 && widget.isMouseOverElement(evt.mx, evt.my)) {
						selection.setText(lootTableName);
						getContainer().setLootTableName(lootTableName);
					}
					return true;
				}
			});
			totalHeight += 12;
			selectionArea.addGuiElement(button);
		}

		selectionArea.setAreaSize(totalHeight + 8);
	}
}
