package com.matag.cards.ability.type;

import java.util.Objects;

public enum AbilityType {
  // Keyword
  DEATHTOUCH("Deathtouch."),
//  DEFENDER(null),
  DOUBLE_STRIKE("Double strike."),
  ENCHANT("Enchanted creature gets %s."),
  EQUIP("Equipped creature gets %s."),
  FIRST_STRIKE("First strike."),
  FLASH("Flash."),
  FLYING("Flying."),
  HASTE("Haste."),
//  HEXPROOF(null),
  INDESTRUCTIBLE("Indestructible."),
//  INTIMIDATE(null),
//  LANDWALK(null),
  LIFELINK("Lifelink."),
//  PROTECTION(null),
  REACH("Reach."),
//  SHROUD(null),
  TRAMPLE("Trample."),
  VIGILANCE("Vigilance."),
//  BANDING(null),
//  RAMPAGE(null),
//  CUMULATIVE_UPKEEP(null),
//  FLANKING(null),
//  PHASING(null),
//  BUYBACK(null),
//  SHADOW(null),
//  CYCLING(null),
//  ECHO(null),
//  HORSEMANSHIP(null),
//  FADING(null),
//  KICKER(null),
//  FLASHBACK(null),
//  MADNESS(null),
//  FEAR(null),
//  MORPH(null),
//  AMPLIFY(null),
//  PROVOKE(null),
//  STORM(null),
//  AFFINITY(null),
//  ENTWINE(null),
//  MODULAR(null),
//  SUNBURST(null),
//  BUSHIDO(null),
//  SOULSHIFT(null),
//  SPLICE(null),
//  OFFERING(null),
//  NINJUTSU(null),
//  EPIC(null),
//  CONVOKE(null),
//  DREDGE(null),
//  TRANSMUTE(null),
//  BLOODTHIRST(null),
//  HAUNT(null),
//  REPLICATE(null),
//  FORECAST(null),
//  GRAFT(null),
//  RECOVER(null),
//  RIPPLE(null),
//  SPLIT_SECOND(null),
//  SUSPEND(null),
//  VANISHING(null),
//  ABSORB(null),
//  AURA_SWAP(null),
//  DELVE(null),
//  FORTIFY(null),
//  FRENZY(null),
//  GRAVESTORM(null),
//  POISONOUS(null),
//  TRANSFIGURE(null),
//  CHAMPION(null),
//  CHANGELING(null),
//  EVOKE(null),
//  HIDEAWAY(null),
//  PROWL(null),
//  REINFORCE(null),
//  CONSPIRE(null),
//  PERSIST(null),
//  WITHER(null),
//  RETRACE(null),
//  DEVOUR(null),
//  EXALTED(null),
//  UNEARTH(null),
//  CASCADE(null),
//  ANNIHILATOR(null),
//  LEVEL_UP(null),
//  REBOUND(null),
//  TOTEM_ARMOR(null),
//  INFECT(null),
//  BATTLE_CRY(null),
//  LIVING_WEAPON(null),
//  UNDYING(null),
//  MIRACLE(null),
//  SOULBOND(null),
//  OVERLOAD(null),
//  SCAVENGE(null),
//  UNLEASH(null),
//  CIPHER(null),
//  EVOLVE(null),
//  EXTORT(null),
//  FUSE(null),
//  BESTOW(null),
//  TRIBUTE(null),
//  DETHRONE(null),
//  HIDDEN_AGENDA(null),
//  OUTLAST(null),
  PROWESS("Prowess."),
//  DASH(null),
//  EXPLOIT(null),
  MENACE("Menace."),
//  RENOWN(null),
//  AWAKEN(null),
//  DEVOID(null),
//  INGEST(null),
//  MYRIAD(null),
//  SURGE(null),
//  SKULK(null),
//  EMERGE(null),
//  ESCALATE(null),
//  MELEE(null),
//  CREW(null),
//  FABRICATE(null),
//  PARTNER(null),
//  UNDAUNTED(null),
//  IMPROVISE(null),
//  AFTERMATH(null),
//  EMBALM(null),
//  ETERNALIZE(null),
//  AFFLICT(null),
//  ASCEND(null),
//  ASSIST(null),
//  JUMP_START(null),
//  MENTOR(null),
//  AFTERLIFE(null),
//  RIOT(null),
//  SPECTACLE(null),
//  ESCAPE(null),
//  COMPANION(null),
//  MUTATE(null),

  // Others
  ADAMANT("Adamant"),
  ENTERS_THE_BATTLEFIELD_WITH("Enters the battlefield %s."),
  SELECTED_PERMANENTS_GET("%s %s"),
  SHUFFLE_GRAVEYARD_INTO_LIBRARY_FOR_TARGET_PLAYER("Shuffle graveyard into library."),
  TAP_ADD_MANA("Tap add %s mana."),
  THAT_TARGETS_GET("That targets get %s.");

  private String text;

  AbilityType(String text) {
    Objects.requireNonNull(text);
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public static AbilityType abilityType(String text) {
    if (text == null) {
      return null;
    }

    return AbilityType.valueOf(text);
  }
}