/*
 * $Id: TranslationBundle.java,v 1.20 2006/03/21 08:11:15 lexu Exp $
 *
 * Copyright 2003-2005 Wanadoo Nederland B.V.
 * See the COPYRIGHT file for redistribution and use restrictions.
 */
package org.xins.gui.resources;

import java.util.ListResourceBundle;

import org.xins.gui.AppCenter;

/**
 * Base class for the translations resource bundle for the XINS GUI
 * application.
 *
 * @version $Revision: 1.20 $ $Date: 2006/03/21 08:11:15 $
 * @author Ernst de Haan (<a href="mailto:ernst.dehaan@nl.wanadoo.com">ernst.dehaan@nl.wanadoo.com</a>)
 */
public final class TranslationBundle extends ListResourceBundle {

   //------------------------------------------------------------------------
   // Class fields
   //------------------------------------------------------------------------

   private static final Object[][] CONTENTS = {
      { "frames.MainFrame.title",        "Visual XINS"                      },
      { "actions.FileMenu.label",        "File"                             },
      { "actions.FileMenu.mnemonic",     "F"                                },
      { "actions.FileMenu.haveIcon",     "false"                            },
      { "actions.EditMenu.label",        "Edit"                             },
      { "actions.EditMenu.mnemonic",     "E"                                },
      { "actions.EditMenu.haveIcon",     "false"                            },
      { "actions.ProjectMenu.label",     "Project"                          },
      { "actions.ProjectMenu.mnemonic",  "P"                                },
      { "actions.ProjectMenu.haveIcon",  "false"                            },
      { "actions.ViewMenu.label",        "View"                             },
      { "actions.ViewMenu.mnemonic",     "V"                                },
      { "actions.ViewMenu.haveIcon",     "false"                            },
      { "actions.HelpMenu.label",        "Help"                             },
      { "actions.HelpMenu.mnemonic",     "H"                                },
      { "actions.HelpMenu.haveIcon",     "false"                            },

      { "actions.NewProject.label",      "New Project..."                   },
      { "actions.NewProject.mnemonic",   "N"                                },
      { "actions.NewProject.comment",    "Creates a new project"            },
      { "actions.NewProject.accel",      "ctrl N"                           },
      { "actions.OpenProject.label",     "Open Project..."                  },
      { "actions.OpenProject.mnemonic",  "O"                                },
      { "actions.OpenProject.comment",   "Loads an existing project"        },
      { "actions.OpenProject.accel",     "ctrl O"                           },
      { "actions.SaveProject.label",     "Save Project..."                  },
      { "actions.SaveProject.mnemonic",  "S"                                },
      { "actions.SaveProject.comment",   "Saves an existing project"        },
      { "actions.SaveProject.accel",     "ctrl S"                           },
      { "actions.CloseProject.label",    "Close Project"                    },
      { "actions.CloseProject.mnemonic", "C"                                },
      { "actions.CloseProject.comment",  "Closes the current project"       },
      { "actions.CloseProject.accel",    "ctrl W"                           },
      { "actions.NewAPI.label",          "New API..."                       },
      { "actions.NewAPI.mnemonic",       "A"                                },
      { "actions.NewAPI.comment",        "Adds a new API to the project"    },
      { "actions.NewAPI.accel",          "ctrl shift A"                     },
      { "actions.NewFunction.label",     "New Function..."                  },
      { "actions.NewFunction.mnemonic",  "F"                                },
      { "actions.NewFunction.comment",   "Adds a new function to the current API" },
      { "actions.NewFunction.accel",     "ctrl shift F"                     },
      { "actions.Undo.label",            "Undo"                             },
      { "actions.Undo.mnemonic",         "U"                                },
      { "actions.Undo.comment",          "Undoes the previous operation performed by the user" },
      { "actions.Undo.accel",            "ctrl Z"                           },
      { "actions.Redo.label",            "Redo"                             },
      { "actions.Redo.mnemonic",         "R"                                },
      { "actions.Redo.comment",          "Redoes the next operation performed by the user, which was previously undone" },
      { "actions.Redo.accel",            "ctrl Y"                           },
      { "actions.Cut.label",             "Cut"                              },
      { "actions.Cut.mnemonic",          "U"                                },
      { "actions.Cut.comment",           "Places the selection on the clipboard and then removes it from the current context" },
      { "actions.Cut.accel",             "ctrl X"                           },
      { "actions.Copy.label",            "Copy"                             },
      { "actions.Copy.mnemonic",         "C"                                },
      { "actions.Copy.comment",          "Copies the selection to the clipboard" },
      { "actions.Copy.accel",            "ctrl C"                           },
      { "actions.Paste.label",           "Paste"                            },
      { "actions.Paste.mnemonic",        "P"                                },
      { "actions.Paste.comment",         "Pastes the content of the clipboard" },
      { "actions.Paste.accel",           "ctrl V"                           },
      { "actions.EditSettings.label",    "Preferences"                      },
      { "actions.EditSettings.mnemonic", "F"                                },
      { "actions.EditSettings.comment",  "Shows the editable settings for this application" },
      { "actions.Help.label",            "Help"                             },
      { "actions.Help.mnemonic",         "H"                                },
      { "actions.Help.comment",          "Displays the help file"           },
      { "actions.Help.accel",            "F1"                               },
      { "actions.About.label",           "About"                            },
      { "actions.About.mnemonic",        "B"                                },
      { "actions.About.comment",         "Displays information about this application" },
      { "actions.Exit.label",            "Exit"                             },
      { "actions.Exit.mnemonic",         "X"                                },
      { "actions.Exit.comment",          "Exits this application"           },
      { "actions.Exit.accel",            "alt F4"                           },

      { "dialogs.actions.NewProject.title",                     "New XINS project"                                      },
      { "dialogs.actions.NewProject.labels.Name.label",         "Project name:"                                         },
      { "dialogs.actions.NewProject.labels.Name.mnemonic",      "P"                                                     },
      { "dialogs.actions.NewProject.labels.Name.comment",       "The name for the new XINS project"                     },
      { "dialogs.actions.NewProject.labels.Domain.label",       "Domain:"                                               },
      { "dialogs.actions.NewProject.labels.Domain.mnemonic",    "D"                                                     },
      { "dialogs.actions.NewProject.labels.Domain.comment",     "The domain to use when generating Java code, e.g. 'org.myorganisation'" },
      { "dialogs.actions.NewProject.buttons.Ok.label",          "OK"                                                    },
      { "dialogs.actions.NewProject.buttons.Ok.mnemonic",       "O",                                                    },
      { "dialogs.actions.NewProject.buttons.Ok.comment",        "Proceed, create the project"                           },
      { "dialogs.actions.NewProject.buttons.Cancel.label",      "Cancel"                                                },
      { "dialogs.actions.NewProject.buttons.Cancel.mnemonic",   "C",                                                    },
      { "dialogs.actions.NewProject.buttons.Cancel.comment",    "Cancel creating a new project"                         },

      { "dialogs.actions.NewAPI.title",                       "New API"                                                 },
      { "dialogs.actions.NewAPI.labels.Name.label",           "API name:"                                               },
      { "dialogs.actions.NewAPI.labels.Name.mnemonic",        "N"                                                       },
      { "dialogs.actions.NewAPI.labels.Name.comment",         "The (unique) name for the new API"                       },
      { "dialogs.actions.NewAPI.labels.Description.label",    "Description:"                                            },
      { "dialogs.actions.NewAPI.labels.Description.mnemonic", "D"                                                       },
      { "dialogs.actions.NewAPI.labels.Description.comment",  "Text describing the purpose/scope of this API"           },
      { "dialogs.actions.NewAPI.buttons.Ok.label",            "OK"                                                      },
      { "dialogs.actions.NewAPI.buttons.Ok.mnemonic",         "O",                                                      },
      { "dialogs.actions.NewAPI.buttons.Ok.comment",          "Proceed, create the API"                                 },
      { "dialogs.actions.NewAPI.buttons.Cancel.label",        "Cancel"                                                  },
      { "dialogs.actions.NewAPI.buttons.Cancel.mnemonic",     "C",                                                      },
      { "dialogs.actions.NewAPI.buttons.Cancel.comment",      "Cancel creating a new API"                               },

      { "dialogs.actions.NewFunction.title",                       "New function"                                                 },
      { "dialogs.actions.NewFunction.labels.Name.label",           "Function name:"                                               },
      { "dialogs.actions.NewFunction.labels.Name.mnemonic",        "N"                                                       },
      { "dialogs.actions.NewFunction.labels.Name.comment",         "The (unique) name for the new function"                  },
      { "dialogs.actions.NewFunction.labels.Description.label",    "Description:"                                            },
      { "dialogs.actions.NewFunction.labels.Description.mnemonic", "D"                                                       },
      { "dialogs.actions.NewFunction.labels.Description.comment",  "Text describing the purpose/scope of this function"      },
      { "dialogs.actions.NewFunction.buttons.Ok.label",            "OK"                                                      },
      { "dialogs.actions.NewFunction.buttons.Ok.mnemonic",         "O",                                                      },
      { "dialogs.actions.NewFunction.buttons.Ok.comment",          "Proceed, create the function"                            },
      { "dialogs.actions.NewFunction.buttons.Cancel.label",        "Cancel"                                                  },
      { "dialogs.actions.NewFunction.buttons.Cancel.mnemonic",     "C",                                                      },
      { "dialogs.actions.NewFunction.buttons.Cancel.comment",      "Cancel creating a new function"                          },

      { "dialogs.actions.About.title",               "About..."            },
      { "dialogs.actions.About.buttons.Ok.label",    "OK"                  },
      { "dialogs.actions.About.buttons.Ok.mnemonic", "O",                  },
      { "dialogs.actions.About.buttons.Ok.comment",  "Dismiss this dialog" },
      { "dialogs.actions.About.labels.Text.label",   "<HTML><B>Visual XINS 0.1</B><BR>"
                                                   + "Copyright 2005, Wanadoo Nederland B.V.<BR><BR>"
                                                   + "Developed by Ernst de Haan and Lex Uijthof.<BR><BR>"
                                                   + "Icons are based on the Crystal SVG icons, by Everaldo Coelho." },

      { "panels.ProjectOverview.labels.Name.label",   "Name:"   },
      { "panels.ProjectOverview.labels.Domain.label", "Domain:" },

      { "panels.APIOverview.labels.Name.label",        "Name:"        },
      { "panels.APIOverview.labels.Description.label", "Description:" },

      { "panels.FunctionOverview.labels.Name.label",        "Name:"   },
      { "panels.FunctionOverview.labels.Description.label", "Description:" },
   };


   //------------------------------------------------------------------------
   // Class functions
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Constructors
   //------------------------------------------------------------------------

   /**
    * Constructs a new <code>TranslationBundle</code> instance.
    */
   public TranslationBundle() {
      // empty
   }


   //------------------------------------------------------------------------
   // Fields
   //------------------------------------------------------------------------

   //------------------------------------------------------------------------
   // Methods
   //------------------------------------------------------------------------

   /**
    * Returns all translations.
    *
    * @return
    *    all translations.
    */
   public Object[][] getContents() {
      return CONTENTS;
   }
}
