package edu.baylor.ecs.cloudhubs.service;

import edu.baylor.ecs.seer.common.FlowNode;
import edu.baylor.ecs.seer.common.entity.SeerFlowMethodRepresentation;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.analysis.FramePrinter;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


@Service
public class BytecodeFlowStructureService {

  void process(List<SeerFlowMethodRepresentation> methodRepresentations) {

    // Loop through every class in the array
    for (SeerFlowMethodRepresentation methodRepresentation : methodRepresentations) {

      // Setup some initial objects
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PrintStream out = new PrintStream(baos);
      FramePrinter fp = new FramePrinter(out);

      // Attempt to retrieve the class
      CtClass clazz = null;
      try {
        clazz = ClassPool.getDefault().getCtClass(methodRepresentation.getClassName());
      } catch (NotFoundException e) {
        System.out.println(e.toString());
      }

      // If retrieval of the class failed then don't worry about the nodes
      if (clazz == null) {
        methodRepresentation.setNodes(new TreeMap<>());
        continue;
      }

      // Attempt to retrieve the method
      CtMethod method = null;
      try {
        method = clazz.getDeclaredMethod(methodRepresentation.getMethodName());

        if (method != null) {
          fp.print(method);
        }

      } catch (Exception e) {
        System.out.println(e.toString());
      }

      // Retrieve the bytecode from the ByteArrayOutputStream
      String bytecode = new String(baos.toByteArray(), StandardCharsets.UTF_8);

      // Build the structure for parsing
      Map<Integer, FlowNode> nodes = processBytecode(bytecode);
      methodRepresentation.setNodes(nodes);
    }

  }

  private Map<Integer, FlowNode> processBytecode(String bytecode) {

    // Filter out garbage lines in the bytecode
    List<String> processed = preprocessBytecode(bytecode);

    // Initialize the map for post-processing
    Map<Integer, FlowNode> map = new HashMap<>();

    for (String s : processed) {

      // Split the method bytecode string based on newlines so each command is a different index
      String[] arr = s.split("\n");

      FlowNode current = null;
      FlowNode root = null;

      // Loop through every command, skipping the method header
      for (int i = 1; i < arr.length; i++) {

        // Get the current command
        String line = arr[i];

        // Check that the command exists
        if (line != null && ! line.equals("")) {
          // Split the command into it's metadata
          String[] split = line.trim().split(" ");

          // Pull out the id and the body of the command
          String id = split[0].substring(0, split[0].length() - 1);
          String command = split[1];
          String type = "general";

          // Determine the type of the command
          if (command.equals("goto")) {
            type = "goto";
          } else if (command.startsWith("if")) {
            type = "conditional";
          } else if (command.startsWith("invoke")) {
            type = "method";
          } else if (command.contains("return")) {
            type = "return";
          }

          // Create a new flowNode with id and type
          FlowNode flowNode = new FlowNode(id, type);
          // Set the flowNode's raw data
          flowNode.setRaw(line);

          // Put the flowNode into the map for post-processing later
          map.put(Integer.parseInt(id), flowNode);

          // If the root to this tree is null, initialize a new root
          if (current == null) {
            current = flowNode;
            // Ret the superoot
            root = current;
          } else {
            // If there is a currentNode then assume sequential ordering and add the new child
            current.addChild(flowNode);
            current = flowNode;
          }
        }
      }

      // Post-Processing for building correct ordering
      for (Map.Entry<Integer, FlowNode> entry : map.entrySet()) {

        // If the node is a conditional:
        //      add the new child from map
        switch (entry.getValue().getType()) {
          case "conditional": {
            String[] values = entry.getValue().getRaw().split(" ");
            Integer next = Integer.parseInt(values[2]);
            FlowNode n = map.get(next);
            if (n != null) {
              entry.getValue().addChild(n);
            }
            break;
          }
          // If the node is a goto
          //      break the existing condition
          //      add the new child from map
          case "goto": {
            String[] values = entry.getValue().getRaw().split(" ");

            Iterator<Integer> it = entry.getValue().getChildren().iterator();
            while (it.hasNext()) {
              it.next();
              it.remove();
            }

            Integer next = Integer.parseInt(values[2]);
            FlowNode n = map.get(next);
            if (n != null) {
              entry.getValue().addChild(n);
            }
            break;
          }
          // If the node is a return
          //      remove existing children
          case "return": {
            Iterator<Integer> it = entry.getValue().getChildren().iterator();
            while (it.hasNext()) {
              it.next();
              it.remove();
            }
            break;
          }
        }

      }
    }

    return map;
  }

  // Processing the bytecode will create a tree of nodes that will show the flow of the nodes

  /**
   * This method constructs a {@link List} of {@link String} objects, each of which is a line of
   * bytecode from a {@link String} of the raw bytecode from {@link FramePrinter#print(CtMethod)}.
   * The purpose of preprocessing is to remove any methods that are abstract or have no body and
   * also to break up each method into a separate string. This is a private helper method called
   * from {@link BytecodeFlowStructureService#processBytecode(String)}.
   *
   * @param bytecode a {@link String} representing the raw bytecode for a method
   * @return a {@link List} of {@link String} objects, each of which is a line of bytecode
   * @see
   * <a href="http://www.javassist.org/tutorial/tutorial3.html">http://www.javassist.org/tutorial/tutorial3.html</a>
   */
  private List<String> preprocessBytecode(String bytecode) {
    // Setup some initial strctures
    List<String> storage = new ArrayList<>();
    String currentMethod = "";

    // Setup a reader around the bytecode string
    BufferedReader reader = new BufferedReader(new StringReader(bytecode));
    String line = null;
    do {

      // Read the line
      try {
        line = reader.readLine();
      } catch (Exception e) {
        System.out.println(e.toString());
      }

      // If it is a real line
      if (line != null && ! line.equals("")) {

        // Split it on spaces to extract out the metadata
        String[] arr = line.trim().split(" ");

        if (arr.length > 0) {
          // Not one of the stack or locals lines
          if (! arr[0].trim().equals("stack") && ! arr[0].trim().equals("locals")) {

            // If it doesn't start with a digit then it's a method heading
            if (! Character.isDigit(arr[0].charAt(0))) {

              // If the current method has body, i.e this method is a new one, add it to the structure
              if (! currentMethod.equals("")) {
                storage.add(currentMethod);
              }

              // Reset the current method
              currentMethod = "";
            }

            // Add the line to current method
            currentMethod = currentMethod.concat(line + "\n");

          }
        }
      }

    } while (line != null);

    // If the current method has body add it to the structure
    // This is in case there is a method in the pipeline
    if (! currentMethod.equals("")) {
      storage.add(currentMethod);
    }

    // Reset the current method
    currentMethod = "";

    // This will remove any functions that have no body
    Iterator<String> it = storage.iterator();
    while (it.hasNext()) {
      String s = it.next();
      String[] arr = s.split("\n");
      if (! (arr.length > 1)) {
        it.remove();
      }
    }

    return storage;
  }

  private Map<Integer, FlowNode> postProcessBytecode(Map<Integer, FlowNode> map) {

    Set<Integer> importantNodes = new HashSet<>();
    importantNodes.add(0);

    // Filter out unimportant node
    for (Map.Entry<Integer, FlowNode> entry : map.entrySet()) {

      String type = entry.getValue().getType();

      // If it's a method or return then we want it so add
      if (type.equals("method") || type.equals("return")) {
        importantNodes.add(entry.getKey());
      }

      // If it's a conditional or goto then we want the node and both it's children, even if one of the children
      // is a normal node
      if (type.equals("conditional") || type.equals("goto")) {
        // Add the node
        importantNodes.add(entry.getKey());
        // Add the children
        importantNodes.addAll(entry.getValue().getChildren());
      }
    }

    // Sort the list of nodes by their key
    List<Integer> sortedList = new ArrayList<>(importantNodes);
    Collections.sort(sortedList);

    // Rebuild tree
    for (int i = 0; i < sortedList.size() - 1; i++) {
      Integer key = sortedList.get(i);
      // If it is a conditional or a goto node then it's children are already correct
      if (! map.get(key).getType().equals("conditional") && ! map.get(key).getType().equals("goto")) {
        // Clear the existing children and add the next child
        map.get(key).setChildren(new ArrayList<>());
        map.get(sortedList.get(i + 1)).setParents(new ArrayList<>());
        map.get(key).addChild(map.get(sortedList.get(i + 1)));
      } else if (map.get(key).getType().equals("goto")) {
        map.get(sortedList.get(i + 1)).removeParent(map.get(key));
      }
    }

    // Remove any nodes from the map that aren't needed anymore
    map.keySet().removeIf(e -> ! sortedList.contains(e));

    // Return the sorted map
    return new TreeMap<>(map);
  }
}
